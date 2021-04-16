package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.TrendingTvShowsDao
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage.TrendingTvShow
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import com.aero51.moviedatabase.utils.Constants.TV_SHOWS_FIRST_PAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingTvShowsBoundaryCallback(application: Application?, private val executors: AppExecutors) : BoundaryCallback<TrendingTvShow>() {
    private val database: Database
    private val dao: TrendingTvShowsDao
    private val networkState: MutableLiveData<NetworkState>
    val current_Tv_shows_page: LiveData<TrendingTvShowsPage>

    init {
        database = Database.getInstance(application)
        dao = database._trending_tv_shows_dao
        networkState = MutableLiveData()
        current_Tv_shows_page = dao.liveDataTvShowPage
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "trending tv shows onzeroitemsloaded");
        fetchTrendingTvShows(TV_SHOWS_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: TrendingTvShow) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "trending tv shows onItemAtFrontLoaded,item:" + itemAtFront.name)
    }

    override fun onItemAtEndLoaded(itemAtEnd: TrendingTvShow) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_Tv_shows_page.value!!.page + 1
        //Log.d(Constants.LOG, "trending tv shows onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchTrendingTvShows(page_number)
    }

    fun fetchTrendingTvShows(pageNumber: Int) {
        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getTrendingTvShows(TMDB_API_KEY, pageNumber)
        call.enqueue(object : Callback<TrendingTvShowsPage?> {
            override fun onResponse(call: Call<TrendingTvShowsPage?>, response: Response<TrendingTvShowsPage?>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "trending tv shows Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "trending tv shows Response ok: " + response.code())
                val mTrendingTvShows = response.body()
                insertListToDb(mTrendingTvShows)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<TrendingTvShowsPage?>, t: Throwable) {
                Log.d(Constants.LOG, "trending tv shows onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))
            }
        })
    }

    fun insertListToDb(page: TrendingTvShowsPage?) {
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