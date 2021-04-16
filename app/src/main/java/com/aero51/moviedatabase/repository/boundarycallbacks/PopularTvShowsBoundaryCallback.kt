package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.PopularTvShowsDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage.PopularTvShow
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.REGION
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import com.aero51.moviedatabase.utils.Constants.TV_SHOWS_FIRST_PAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularTvShowsBoundaryCallback(application: Application?, private val executors: AppExecutors) : BoundaryCallback<PopularTvShow>() {
    private val database: Database
    private val dao: PopularTvShowsDao
    private val networkState: MutableLiveData<NetworkState>
    val current_Tv_shows_page: LiveData<PopularTvShowsPage>

    init {
        database = Database.getInstance(application)
        dao = database._popular_tv_shows_dao
        networkState = MutableLiveData()
        current_Tv_shows_page = dao.liveDataTvShowPage
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "popular tv shows onzeroitemsloaded");
        fetchPopularTvShows(TV_SHOWS_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: PopularTvShow) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "popular tv shows onItemAtFrontLoaded,item:" + itemAtFront.name)
    }

    override fun onItemAtEndLoaded(itemAtEnd: PopularTvShow) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_Tv_shows_page.value!!.page + 1
        //Log.d(Constants.LOG, "popular tv shows onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularTvShows(page_number)
    }

    fun fetchPopularTvShows(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getPopularTvShows(TMDB_API_KEY, pageNumber, REGION, "popularity.desc", 2500)
        //Call<PopularTvShowsPage> call = theMovieDbApi.getPopularTvShows(TMDB_API_KEY, pageNumber,REGION);
        call.enqueue(object : Callback<PopularTvShowsPage?> {
            override fun onResponse(call: Call<PopularTvShowsPage?>, response: Response<PopularTvShowsPage?>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "popular tv shows Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "popular tv shows Response ok: " + response.code())
                val mPopularTvShows = response.body()
                insertListToDb(mPopularTvShows)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<PopularTvShowsPage?>, t: Throwable) {
                Log.d(Constants.LOG, "popular tv shows onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: PopularTvShowsPage?) {
        val listOfResults = page!!.results
        val runnable = Runnable {
            dao.deleteAllTvShowPages()
            dao.insertTvShowPage(page)
            val currentTime: Long =System.currentTimeMillis()
            for(tvShow in listOfResults)
            {
                tvShow.timestamp=currentTime

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