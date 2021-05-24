package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.UpcomingMoviesDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE
import com.aero51.moviedatabase.utils.Constants.REGION
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingMoviesBoundaryCallback(application: Application?, private val executors: AppExecutors) : BoundaryCallback<UpcomingMovie>() {
    private val database: Database
    private val dao: UpcomingMoviesDao
    private val networkState: MutableLiveData<NetworkState>
    val current_movie_page: LiveData<UpcomingMoviesPage>

    init {
        //super();
        database = Database.getInstance(application)
        dao = database._upcoming_movies_dao
        networkState = MutableLiveData()
        current_movie_page = dao.liveDataMoviePage
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchUpcomingMovies(MOVIES_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: UpcomingMovie) {
        super.onItemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: UpcomingMovie) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_movie_page.value!!.page + 1
        fetchUpcomingMovies(page_number)
    }

    fun fetchUpcomingMovies(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getUpcomingMovies(TMDB_API_KEY, pageNumber, REGION)
        call.enqueue(object : Callback<UpcomingMoviesPage?> {
            override fun onResponse(call: Call<UpcomingMoviesPage?>, response: Response<UpcomingMoviesPage?>) {
                if (!response.isSuccessful) {
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                val upcomingMoviesPage = response.body()
                insertListToDb(upcomingMoviesPage)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<UpcomingMoviesPage?>, t: Throwable) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: UpcomingMoviesPage?) {
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