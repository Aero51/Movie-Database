package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.boundarycallbacks.*
import com.aero51.moviedatabase.repository.db.*
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.*
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage.AiringTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage.PopularTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage.TrendingTvShow
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class TvShowsRepository(private val application: Application, private val executors: AppExecutors) {

    private val database: Database
    private val genresDao: GenresDao
    private lateinit var popularTvShowsDao: PopularTvShowsDao
    private lateinit var airingTvShowsDao: AiringTvShowsDao
    private lateinit var trendingTvShowsDao: TrendingTvShowsDao

    var tvPopularResultsPagedList: LiveData<PagedList<PopularTvShow>>? = null
        private set
    var tvAiringResultsPagedList: LiveData<PagedList<AiringTvShow>>? = null
        private set
    var tvTrendingResultsPagedList: LiveData<PagedList<TrendingTvShow>>? = null
        private set
    var tvShowsByGenrePagedList: LiveData<PagedList<TvShowsByGenrePage.TvShowByGenre>>? = null
        private set

    private val popularTvShowsBoundaryCallback: PopularTvShowsBoundaryCallback
    private val airingTvShowsBoundaryCallback: AiringTvShowsBoundaryCallback
    private val trendingTvShowsBoundaryCallback: TrendingTvShowsBoundaryCallback
    private lateinit var tvShowsByGenreBoundaryCallback: TvShowsByGenreBoundaryCallback

    private val pagedListConfig: PagedList.Config
        private get() = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build()

    init {
        database = Database.getInstance(application)
        genresDao = database._genres_dao
        popularTvShowsDao = database._popular_tv_shows_dao
        airingTvShowsDao = database._airing_tv_shows_dao
        trendingTvShowsDao = database._trending_tv_shows_dao
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


    private fun createAiringTvShowsPagedList(dao: AiringTvShowsDao) {
        tvAiringResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(airingTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createTrendingTvShowsPagedList(dao: TrendingTvShowsDao) {
        tvTrendingResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(trendingTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createTvShowsByGenrePagedList(dao: GenresDao, genreId: Int) {
        tvShowsByGenreBoundaryCallback = TvShowsByGenreBoundaryCallback(application, executors, genreId)

        tvShowsByGenrePagedList = LivePagedListBuilder(dao.getTvShowsByGenre(genreId), pagedListConfig)
                .setBoundaryCallback(tvShowsByGenreBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    val lastPopularTvShowPage: LiveData<PopularTvShowsPage>
        get() = popularTvShowsBoundaryCallback.current_Tv_shows_page
    val popularNetworkState: LiveData<NetworkState>
        get() = popularTvShowsBoundaryCallback.getNetworkState()

    val lastAiringTvShowPage: LiveData<AiringTvShowsPage>
        get() = airingTvShowsBoundaryCallback.current_Tv_shows_page
    val airingNetworkState: LiveData<NetworkState>
        get() = airingTvShowsBoundaryCallback.getNetworkState()

    val lastTrendingTvShowPage: LiveData<TrendingTvShowsPage>
        get() = trendingTvShowsBoundaryCallback.current_Tv_shows_page
    val trendingNetworkState: LiveData<NetworkState>
        get() = trendingTvShowsBoundaryCallback.getNetworkState()

    val lastTvShowsByGenrePage: LiveData<TvShowsByGenrePage>
        get() = tvShowsByGenreBoundaryCallback.current_page



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


    fun loadTvShowsByGenre(genreId: Int): LiveData<PagedList<TvShowsByGenrePage.TvShowByGenre>>? {
        createTvShowsByGenrePagedList(genresDao, genreId)
        return tvShowsByGenrePagedList
    }


    suspend fun checkIfTvShowsByGenreNeedsRefresh(genreId: Int) {
        val tvShowByGenre = genresDao.getFirstTvShowByGenre(genreId)
        if (tvShowByGenre != null) {
            Log.d(Constants.LOG2, "MoviesRepository timestamp: " + tvShowByGenre.timestamp)
            val currentTime: Long = System.currentTimeMillis()
            if((currentTime - Constants.ONE_WEEK_IN_MILLIS) >= tvShowByGenre.timestamp){
                // TODO    refresh implemented, need to clean it
                genresDao.deleteAllMoviesByGenrePagesSuspended()
                genresDao.deleteAllMoviesByGenre(genreId)
            }
        }
    }

    suspend fun checkIfPopularTvShowsNeedsRefresh() {
        val popularTvShow = popularTvShowsDao.getFirstPopularTvShow()
        if (popularTvShow != null) {
            Log.d(Constants.LOG2, "MoviesRepository timestamp: " + popularTvShow.timestamp)
            val currentTime: Long = System.currentTimeMillis()
            if((currentTime - Constants.ONE_WEEK_IN_MILLIS) >= popularTvShow.timestamp){
                // TODO    refresh implemented, need to clean it
                popularTvShowsDao.deleteAllPopularTvShowsPagesSuspended()
                popularTvShowsDao.deleteAllPopularTvShowsSuspended()
            }
        }
    }

    suspend fun checkIfTrendingTvShowsNeedsRefresh() {
        val trendingTvShow = trendingTvShowsDao.getFirstTrendingTvShow()
        if (trendingTvShow != null) {
            Log.d(Constants.LOG2, "MoviesRepository timestamp: " + trendingTvShow.timestamp)
            val currentTime: Long = System.currentTimeMillis()
            if((currentTime - Constants.ONE_WEEK_IN_MILLIS) >= trendingTvShow.timestamp){
                // TODO    refresh implemented, need to clean it
                trendingTvShowsDao.deleteAllTrendingTvShowsPagesSuspended()
                trendingTvShowsDao.deleteAllTrendingTvShowsSuspended()
            }
        }
    }


    suspend fun checkIfAiringTvShowsNeedsRefresh() {
        val airingTvShow = airingTvShowsDao.getFirstAiringTvShow()
        if (airingTvShow != null) {
            Log.d(Constants.LOG2, "MoviesRepository timestamp: " + airingTvShow.timestamp)
            val currentTime: Long = System.currentTimeMillis()
            if((currentTime - Constants.ONE_WEEK_IN_MILLIS) >= airingTvShow.timestamp){
                // TODO    refresh implemented, need to clean it
                airingTvShowsDao.deleteAllAiringTvShowsPagesSuspended()
                airingTvShowsDao.deleteAllAiringTvShowsSuspended()
            }
        }
    }


}