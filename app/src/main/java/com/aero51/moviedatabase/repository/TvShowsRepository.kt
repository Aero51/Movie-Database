package com.aero51.moviedatabase.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.boundarycallbacks.AiringTvShowsBoundaryCallback
import com.aero51.moviedatabase.repository.boundarycallbacks.PopularTvShowsBoundaryCallback
import com.aero51.moviedatabase.repository.boundarycallbacks.TrendingTvShowsBoundaryCallback
import com.aero51.moviedatabase.repository.db.*
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage.AiringTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage.PopularTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage.TrendingTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class TvShowsRepository(private val application: Application, private val executors: AppExecutors) {

    private val database: Database
    private val genresDao: GenresDao

    var tvPopularResultsPagedList: LiveData<PagedList<PopularTvShow>>? = null
        private set
    var tvAiringResultsPagedList: LiveData<PagedList<AiringTvShow>>? = null
        private set
    var tvTrendingResultsPagedList: LiveData<PagedList<TrendingTvShow>>? = null
        private set
    private val popularTvShowsBoundaryCallback: PopularTvShowsBoundaryCallback
    private val airingTvShowsBoundaryCallback: AiringTvShowsBoundaryCallback
    private val trendingTvShowsBoundaryCallback: TrendingTvShowsBoundaryCallback


    init {
        database = Database.getInstance(application)
        genresDao = database._genres_dao
        val popularTvShowsDao = database._popular_tv_shows_dao
        val airingTvShowsDao = database._airing_tv_shows_dao
        val trendingTvShowsDao = database._trending_tv_shows_dao
        popularTvShowsBoundaryCallback = PopularTvShowsBoundaryCallback(application, executors)
        airingTvShowsBoundaryCallback = AiringTvShowsBoundaryCallback(application, executors)
        trendingTvShowsBoundaryCallback = TrendingTvShowsBoundaryCallback(application, executors)
        createPopularTvShowsPagedList(popularTvShowsDao)
        createAiringTvShowsPagedList(airingTvShowsDao)
        createTrendingTvShowsPagedList(trendingTvShowsDao)
    }


    private fun createPopularTvShowsPagedList(dao: PopularTvShowsDao) {
        tvPopularResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(popularTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    val lastPopularTvShowPage: LiveData<PopularTvShowsPage>
        get() = popularTvShowsBoundaryCallback.current_Tv_shows_page
    val popularNetworkState: LiveData<NetworkState>
        get() = popularTvShowsBoundaryCallback.networkState

    private fun createAiringTvShowsPagedList(dao: AiringTvShowsDao) {
        tvAiringResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(airingTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    val lastAiringTvShowPage: LiveData<AiringTvShowsPage>
        get() = airingTvShowsBoundaryCallback.current_Tv_shows_page
    val airingNetworkState: LiveData<NetworkState>
        get() = airingTvShowsBoundaryCallback.getNetworkState()

    private fun createTrendingTvShowsPagedList(dao: TrendingTvShowsDao) {
        tvTrendingResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(trendingTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    val lastTrendingTvShowPage: LiveData<TrendingTvShowsPage>
        get() = trendingTvShowsBoundaryCallback.current_Tv_shows_page
    val trendingNetworkState: LiveData<NetworkState>
        get() = trendingTvShowsBoundaryCallback.networkState

    fun loadTvShowsGenres(): LiveData<Resource<List<TvShowGenre>>> {
        return object : NetworkBoundResource<TvShowGenresResponse, List<TvShowGenresResponse.TvShowGenre>>(executors) {

            override fun shouldFetch(data: List<TvShowGenre>?): Boolean {
                return data!!.size == 0
            }

            override fun loadFromDb(): LiveData<List<TvShowGenre>> {
                return genresDao.tvShowsGenres
            }

            override fun createCall(): LiveData<ApiResponse<TvShowGenresResponse>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveTvGenres(Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: TvShowGenresResponse) {
                //this is executed on background thread
                database.runInTransaction { genresDao.insertTvShowGenreList(item.genres) }
            }

        }.asLiveData()
    }

    private val pagedListConfig: PagedList.Config
        private get() = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build()


}