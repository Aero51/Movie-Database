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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowsByGenrePage
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowsByGenreBoundaryCallback (application: Application?, private val executors: AppExecutors, genre_Id: Int) : PagedList.BoundaryCallback<TvShowsByGenrePage.TvShowByGenre>(){
    private val database: Database
    private val dao: GenresDao
    private val networkState: MutableLiveData<NetworkState>
    val current_page: LiveData<TvShowsByGenrePage>
    private  var genreId:Int=0

    init {
        // super();
        database = Database.getInstance(application)
        dao = database._genres_dao
        networkState = MutableLiveData()
        current_page = dao.liveDataTvShowsByGenrePage
        this.genreId=genre_Id
    }


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback onzeroitemsloaded");
        fetchMoviesByGenre(Constants.MOVIES_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: TvShowsByGenrePage.TvShowByGenre) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback onItemAtFrontLoaded,item:" + itemAtFront.title)
    }

    override fun onItemAtEndLoaded(itemAtEnd: TvShowsByGenrePage.TvShowByGenre) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_page.value!!.page + 1
        //Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchMoviesByGenre(page_number)
    }


    fun fetchMoviesByGenre(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        //to exclude animation tv shows from results except if animation genre is selected
        var voteCount=500
        var animationId = 16
        if(genreId==animationId)
        {
            animationId=0
        }
        //if genre id is news
        if(genreId==10763)
        {
        voteCount=0
        }

        val call = theMovieDbApi.getTvShowsByGenre(Constants.TMDB_API_KEY, pageNumber,genreId,"vote_average.desc",voteCount,animationId)
        call.enqueue(object : Callback<TvShowsByGenrePage> {
            override fun onResponse(call: Call<TvShowsByGenrePage>, response: Response<TvShowsByGenrePage>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback Response ok: " + response.code())
                val mMoviesByGenre = response.body()
                if (mMoviesByGenre != null) {
                    insertListToDb(mMoviesByGenre)
                }
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<TvShowsByGenrePage>, t: Throwable) {
                Log.d(Constants.LOG, "TvShowsByGenreBoundaryCallback onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: TvShowsByGenrePage) {
        val listOfResults = page!!.results_list
        val runnable = Runnable {
            dao.deleteAllTvShowsByGenrePages()
            dao.insertTvShowsByGenrePage(page)
            val currentTime: Long =System.currentTimeMillis()
            for (tv_show in listOfResults)
            {
                tv_show.timestamp=currentTime
                tv_show.genreId=genreId
            }
            dao.insertTvShowsByGenreList(listOfResults)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }





}