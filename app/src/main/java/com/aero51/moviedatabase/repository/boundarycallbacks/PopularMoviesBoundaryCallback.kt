package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.PopularMoviesDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE
import com.aero51.moviedatabase.utils.Constants.REGION
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesBoundaryCallback(application: Application?, private val executors: AppExecutors) : BoundaryCallback<PopularMovie>() {
    private val database: Database
    private val dao: PopularMoviesDao
    private val networkState: MutableLiveData<NetworkState>
    val current_movie_page: LiveData<PopularMoviesPage>

    init {
        // super();
        database = Database.getInstance(application)
        dao = database._popular_movies_dao
        networkState = MutableLiveData()
        current_movie_page = dao.liveDataMoviePage
    }


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "popularMovies onzeroitemsloaded");
        fetchPopularMovies(MOVIES_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: PopularMovie) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "popularMovies onItemAtFrontLoaded,item:" + itemAtFront.title)
    }

    override fun onItemAtEndLoaded(itemAtEnd: PopularMovie) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_movie_page.value!!.page + 1
        //Log.d(Constants.LOG, "popularMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularMovies(page_number)
    }

    fun fetchPopularMovies(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getPopularMovies(TMDB_API_KEY, pageNumber, REGION, 2000, "popularity.desc")
        call.enqueue(object : Callback<PopularMoviesPage?> {
            override fun onResponse(call: Call<PopularMoviesPage?>, response: Response<PopularMoviesPage?>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "popularMovies Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "popularMovies Response ok: " + response.code())
                val mPopularMovies = response.body()
                insertListToDb(mPopularMovies)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<PopularMoviesPage?>, t: Throwable) {
                Log.d(Constants.LOG, "popularMovies onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: PopularMoviesPage?) {
        val listOfResults = page!!.results_list
        val runnable = Runnable {
            dao.deleteAllMoviePages()
            dao.insertMoviePage(page)
            dao.insertList(listOfResults)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }


}