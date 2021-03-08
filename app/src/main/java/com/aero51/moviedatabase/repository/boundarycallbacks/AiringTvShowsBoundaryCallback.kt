package com.aero51.moviedatabase.repository.boundarycallbacks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import com.aero51.moviedatabase.repository.db.AiringTvShowsDao
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage.AiringTvShow
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.REGION
import com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY
import com.aero51.moviedatabase.utils.Constants.TV_SHOWS_FIRST_PAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AiringTvShowsBoundaryCallback(application: Application, private val executors: AppExecutors) : BoundaryCallback<AiringTvShow>() {
    private val database: Database
    private val dao: AiringTvShowsDao
    private val networkState: MutableLiveData<NetworkState>
    val current_Tv_shows_page: LiveData<AiringTvShowsPage>
    init {
        database = Database.getInstance(application)
        dao = database._airing_tv_shows_dao
        networkState = MutableLiveData()
        current_Tv_shows_page = dao.liveDataAiringTvShowPage
    }
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //Log.d(Constants.LOG, "popular tv shows onzeroitemsloaded");
        fetchPopularTvShows(TV_SHOWS_FIRST_PAGE)
    }

    override fun onItemAtFrontLoaded(itemAtFront: AiringTvShow) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.d(Constants.LOG, "airing tv shows onItemAtFrontLoaded,item:" + itemAtFront.name)
    }

    override fun onItemAtEndLoaded(itemAtEnd: AiringTvShow) {
        super.onItemAtEndLoaded(itemAtEnd)
        val page_number = current_Tv_shows_page.value!!.page + 1
        //Log.d(Constants.LOG, "popular tv shows onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularTvShows(page_number)
    }

    fun fetchPopularTvShows(pageNumber: Int) {

        networkState.postValue(NetworkState.LOADING)
        val theMovieDbApi = RetrofitInstance.getTmdbApiService()
        val call = theMovieDbApi.getAiringTvShows(TMDB_API_KEY, pageNumber, REGION)
        call.enqueue(object : Callback<AiringTvShowsPage> {
            override fun onResponse(call: Call<AiringTvShowsPage>, response: Response<AiringTvShowsPage>) {
                if (!response.isSuccessful) {
                    Log.d(Constants.LOG, "airing tv shows Response unsuccesful: " + response.code())
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    return
                }
                Log.d(Constants.LOG, "airing tv shows Response ok: " + response.code())
                val mPopularTvShows = response.body()
                insertListToDb(mPopularTvShows!!)
                networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<AiringTvShowsPage?>, t: Throwable) {
                Log.d(Constants.LOG, "airing tv shows onFailure: " + t.message)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, t.message))

            }
        })
    }

    fun insertListToDb(page: AiringTvShowsPage) {
        val listOfResults = page!!.results
        val runnable = Runnable {
            dao.deleteAllAiringTvShowPages()
            dao.insertAiringTvShowPage(page)
            dao.insertList(listOfResults)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }


}