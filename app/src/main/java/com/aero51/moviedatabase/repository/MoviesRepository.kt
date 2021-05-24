package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.boundarycallbacks.*
import com.aero51.moviedatabase.repository.db.*
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage.TopRatedMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*
import com.aero51.moviedatabase.utils.Constants.LOG2
import com.aero51.moviedatabase.utils.Constants.ONE_WEEK_IN_MILLIS

class MoviesRepository(private val application: Application, private val executors: AppExecutors) {

    private val database: Database
    private var topRatedMoviesDao: TopRatedMoviesDao
    private var popularMoviesDao: PopularMoviesDao
    private var nowPlayingMoviesDao: NowPlayingMoviesDao
    private var upcomingMoviesDao: UpcomingMoviesDao
    private var genresDao: GenresDao
    private lateinit var tmdbVideosDao: TmdbVideosDao


    var topRatedResultsPagedList: LiveData<PagedList<TopRatedMovie>>? = null
        private set
    var popularResultsPagedList: LiveData<PagedList<PopularMovie>>? = null
        private set
    var nowPlayingResultsPagedList: LiveData<PagedList<NowPlayingMovie>>? = null
        private set
    var upcomingResultsPagedList: LiveData<PagedList<UpcomingMovie>>? = null
        private set
    var moviesByGenrePagedList: LiveData<PagedList<MoviesByGenrePage.MovieByGenre>>? = null
        private set
    private val topRatedMoviesBoundaryCallback: TopRatedMoviesBoundaryCallback
    private val popularMoviesBoundaryCallback: PopularMoviesBoundaryCallback
    private val nowPlayingMoviesBoundaryCallback: NowPlayingMoviesBoundaryCallback
    private val upcomingMoviesBoundaryCallback: UpcomingMoviesBoundaryCallback
    private lateinit var moviesByGenreBoundaryCallback: MoviesByGenreBoundaryCallback

    private val pagedListConfig: PagedList.Config
        private get() = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build()

    init {
        database = Database.getInstance(application)
        genresDao = database._genres_dao
        topRatedMoviesDao = database._top_rated_movies_dao
        popularMoviesDao = database._popular_movies_dao
        nowPlayingMoviesDao = database._now_playing_movies_dao
        upcomingMoviesDao = database._upcoming_movies_dao
        genresDao = database._genres_dao
        tmdbVideosDao=database._movie_videos_dao
        topRatedMoviesBoundaryCallback = TopRatedMoviesBoundaryCallback(application, executors)
        popularMoviesBoundaryCallback = PopularMoviesBoundaryCallback(application, executors)
        nowPlayingMoviesBoundaryCallback = NowPlayingMoviesBoundaryCallback(application, executors)
        upcomingMoviesBoundaryCallback = UpcomingMoviesBoundaryCallback(application, executors)
        createTopRatedMoviesPagedList(topRatedMoviesDao)
        createPopularMoviesPagedList(popularMoviesDao)
        createNowPlayingMoviesPagedList(nowPlayingMoviesDao)
        createUpcomingMoviesPagedList(upcomingMoviesDao)
    }


    private fun createTopRatedMoviesPagedList(dao: TopRatedMoviesDao) {
        topRatedResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(topRatedMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createPopularMoviesPagedList(dao: PopularMoviesDao) {
        popularResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createNowPlayingMoviesPagedList(dao: NowPlayingMoviesDao) {
        nowPlayingResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(nowPlayingMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createUpcomingMoviesPagedList(dao: UpcomingMoviesDao) {
        upcomingResultsPagedList = LivePagedListBuilder(dao.allResults, pagedListConfig)
                .setBoundaryCallback(upcomingMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    private fun createMoviesByGenrePagedList(dao: GenresDao, genreId: Int) {
        moviesByGenreBoundaryCallback = MoviesByGenreBoundaryCallback(application, executors, genreId)

        moviesByGenrePagedList = LivePagedListBuilder(dao.getMoviesByGenre(genreId), pagedListConfig)
                .setBoundaryCallback(moviesByGenreBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build()
    }

    val topRatedNetworkState: LiveData<NetworkState>
        get() = topRatedMoviesBoundaryCallback.networkState
    val lastTopRatedMoviePage: LiveData<TopRatedMoviesPage>
        get() = topRatedMoviesBoundaryCallback.current_movie_page
    val popularNetworkState: LiveData<NetworkState>
        get() = popularMoviesBoundaryCallback.getNetworkState()
    val lastPopularMoviePage: LiveData<PopularMoviesPage>
        get() = popularMoviesBoundaryCallback.current_movie_page
    val nowPlayingNetworkState: LiveData<NetworkState>
        get() = nowPlayingMoviesBoundaryCallback.getNetworkState()
    val lastNowPlayingMoviePage: LiveData<NowPlayingMoviesPage>
        get() = nowPlayingMoviesBoundaryCallback.current_movie_page
    val upcomingNetworkState: LiveData<NetworkState>
        get() = upcomingMoviesBoundaryCallback.getNetworkState()
    val lastUpcomingMoviePage: LiveData<UpcomingMoviesPage>
        get() = upcomingMoviesBoundaryCallback.current_movie_page
    val lastMoviesByGenrePage: LiveData<MoviesByGenrePage>
        get() = moviesByGenreBoundaryCallback.current_page


    fun loadMoviesGenres(): LiveData<Resource<List<MovieGenre>>> {
        //return MovieGenresNetworkBoundResource(executors, application).asLiveData()
        return object : NetworkBoundResource<MovieGenresResponse, List<MovieGenresResponse.MovieGenre>>(executors) {

            override fun shouldFetch(data: List<MovieGenre>?): Boolean {
                return data!!.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<MovieGenre>> {
                return genresDao.moviesGenres
            }

            override fun createCall(): LiveData<ApiResponse<MovieGenresResponse>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveMovieGenres(Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MovieGenresResponse) {
                //this is executed on background thread
                database.runInTransaction { genresDao.insertMovieGenreList(item.genres) }
            }

        }.asLiveData()
    }


    fun loadMoviesByGenre(genreId: Int): LiveData<PagedList<MoviesByGenrePage.MovieByGenre>>? {
        createMoviesByGenrePagedList(genresDao, genreId)
        return moviesByGenrePagedList
    }



    suspend fun checkIfMoviesByGenreNeedsRefresh(genreId: Int) {
        val movieByGenre = genresDao.getFirstMovieByGenre(genreId)
        if (movieByGenre != null) {
            val currentTime: Long = System.currentTimeMillis()
            if ((currentTime - ONE_WEEK_IN_MILLIS) >= movieByGenre.timestamp) {
                // TODO    refresh implemented, need to clean it
                genresDao.deleteAllMoviesByGenrePagesSuspended()
                genresDao.deleteAllMoviesByGenre(genreId)

            }
        }
    }

    suspend fun checkIfTopRatedMoviesNeedsRefresh() {
        val topRatedMovie = topRatedMoviesDao.getFirstTopRatedMovie()
        if (topRatedMovie != null) {
            val currentTime: Long = System.currentTimeMillis()
            if ((currentTime - ONE_WEEK_IN_MILLIS) >= topRatedMovie.timestamp) {
                topRatedMoviesDao.deleteAllTopRatedMoviesPagesSuspended()
                topRatedMoviesDao.deleteAllTopRatedMoviesSuspended()

            }
        }
    }
    suspend fun checkIfPopularMoviesNeedsRefresh() {
        val popularMovie = popularMoviesDao.getFirstPopularMovie()
        if (popularMovie != null) {
            val currentTime: Long = System.currentTimeMillis()
            if ((currentTime - ONE_WEEK_IN_MILLIS) >= popularMovie.timestamp) {
                popularMoviesDao.deleteAllPopularMoviesPagesSuspended()
                popularMoviesDao.deleteAllPopularMoviesSuspended()

            }
        }
    }

    suspend fun checkIfNowPlayingMoviesNeedsRefresh() {
        val nowPlayingMovie = nowPlayingMoviesDao.getFirstNowPlayingMovie()
        if (nowPlayingMovie != null) {
            val currentTime: Long = System.currentTimeMillis()
            if ((currentTime - ONE_WEEK_IN_MILLIS) >= nowPlayingMovie.timestamp) {
                nowPlayingMoviesDao.deleteAllNowPlayingMoviesPagesSuspended()
                nowPlayingMoviesDao.deleteAllNowPlayingMoviesSuspended()
            }
        }
    }


    suspend fun checkIfUpcomingMoviesNeedsRefresh() {
        val upcomingMovie = upcomingMoviesDao.getFirstUpcomingMovie()
        if (upcomingMovie != null) {
            val currentTime: Long = System.currentTimeMillis()
            if ((currentTime - ONE_WEEK_IN_MILLIS) >= upcomingMovie.timestamp) {
                upcomingMoviesDao.deleteAllUpcomingMoviesPagesSuspended()
                upcomingMoviesDao.deleteAllUpcomingMoviesSuspended()
            }
        }
    }





}