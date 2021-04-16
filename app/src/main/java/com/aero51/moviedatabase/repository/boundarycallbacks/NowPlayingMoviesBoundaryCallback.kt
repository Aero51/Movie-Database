package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.NowPlayingMoviesDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE
import com.aero51.moviedatabase.utils.Constants.REGION
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingMoviesBoundaryCallback(application: Application?, private val executors: AppExecutors) : BoundaryCallback<NowPlayingMovie>() {
    private val database: Database
    private val dao: NowPlayingMoviesDao
    private val networkState: MutableLiveData<NetworkState>
    val current_movie_page: LiveData<NowPlayingMoviesPage>

    init {
        //super();
        database = Database.getInstance(application)
        dao = database._now_playing_movies_dao
        networkState = MutableLiveData()
        current_movie_page = dao.liveDataMoviePage
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "nowPlayingMovies onzeroitemsloaded");
        fetchNowPlayingMovies(MOVIES_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: NowPlayingMovie) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "nowPlayingMovies onItemAtFrontLoaded,item:" + itemAtFront.title)
    }

    override fun onItemAtEndLoaded(itemAtEnd: NowPlayingMovie) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_movie_page.value!!.page + 1
        //Log.d(Constants.LOG, "nowPlayingMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchNowPlayingMovies(page_number)
    }

    fun fetchNowPlayingMovies(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getNowPlayingMovies(TMDB_API_KEY, pageNumber, REGION)
        call.enqueue(object : Callback<NowPlayingMoviesPage?> {
            override fun onResponse(call: Call<NowPlayingMoviesPage?>, response: Response<NowPlayingMoviesPage?>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "nowPlayingMovies Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "nowPlayingMovies Response ok: " + response.code())
                val mNowPlayingMovies = response.body()
                insertListToDb(mNowPlayingMovies)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<NowPlayingMoviesPage?>, t: Throwable) {
                Log.d(Constants.LOG, "nowPlayingMovies onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: NowPlayingMoviesPage?) {
        val listOfResults = page!!.results_list
        val runnable = Runnable {
            dao.deleteAllMoviePages()
            dao.insertMoviePage(page)
            val currentTime: Long =System.currentTimeMillis()
            for (movie in listOfResults)
            {
                movie.timestamp=currentTime
            }
            dao.insertList(listOfResults)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }


}