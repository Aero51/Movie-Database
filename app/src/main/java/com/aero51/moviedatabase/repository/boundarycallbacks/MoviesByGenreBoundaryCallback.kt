package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.GenresDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesByGenreBoundaryCallback (application: Application?, private val executors: AppExecutors,genre_Id: Int) : PagedList.BoundaryCallback<MoviesByGenrePage.MovieByGenre>(){
    private val database: Database
    private val dao: GenresDao
    private val networkState: MutableLiveData<NetworkState>
    val current_page: LiveData<MoviesByGenrePage>
    private  var genreId:Int=0

    init {
        // super();
        database = Database.getInstance(application)
        dao = database._genres_dao
        networkState = MutableLiveData()
        current_page = dao.liveDataMoviesByGenrePage
        this.genreId=genre_Id
    }


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchMoviesByGenre(Constants.MOVIES_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: MoviesByGenrePage.MovieByGenre) {
        super.onItemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: MoviesByGenrePage.MovieByGenre) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_page.value!!.page + 1
        fetchMoviesByGenre(page_number)
    }


    fun fetchMoviesByGenre(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getMoviesByGenre(Constants.TMDB_API_KEY, pageNumber,genreId,"vote_average.desc",1000)
        call.enqueue(object : Callback<MoviesByGenrePage> {
            override fun onResponse(call: Call<MoviesByGenrePage>, response: Response<MoviesByGenrePage>) {
                if (!response.isSuccessful) {
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                val mMoviesByGenre = response.body()
                if (mMoviesByGenre != null) {
                    insertListToDb(mMoviesByGenre)
                }
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<MoviesByGenrePage>, t: Throwable) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: MoviesByGenrePage) {
        val listOfResults = page!!.results_list
        val runnable = Runnable {
            dao.deleteAllMoviesByGenrePages()
            dao.insertMoviesByGenrePage(page)
            val currentTime: Long =System.currentTimeMillis()
            for (movie in listOfResults)
            {
                movie.timestamp=currentTime
                movie.genreId=genreId
            }
            dao.insertMoviesByGenreList(listOfResults)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }





}