package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.FavouritesDao
import com.aero51.moviedatabase.repository.db.TmdbDetailsDao
import com.aero51.moviedatabase.repository.db.TmdbVideosDao
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class DetailsRepository(application: Application, private val executors: AppExecutors) {


    private val database: Database = Database.getInstance(application)
    private var tmdbVideosDao: TmdbVideosDao
    private var tmdbDetailsDao: TmdbDetailsDao
    private val theMovieDbApi = RetrofitInstance.getTmdbApiService()
    private lateinit var favouritesDao: FavouritesDao

    init {
        tmdbVideosDao = database._movie_videos_dao
        tmdbDetailsDao=database._movie_details_dao
        favouritesDao=database._favourites_dao
    }

    fun loadVideosForMovie(movie_id: Int): LiveData<Resource<List<MovieVideosResponse.MovieVideo>>> {
        return object : NetworkBoundResource<MovieVideosResponse, List<MovieVideosResponse.MovieVideo>>(executors) {

            override fun shouldFetch(data: List<MovieVideosResponse.MovieVideo>?): Boolean {
                return data!!.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<MovieVideosResponse.MovieVideo>> {
                return tmdbVideosDao.getMovieVideos(movie_id)

            }

            override fun createCall(): LiveData<ApiResponse<MovieVideosResponse>> {

                return theMovieDbApi.getLiveVideosForMovie(movie_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MovieVideosResponse) {
                //this is executed on background thread
                Log.d("nikola", "movie videos.size: " + item.results.size)
                database.runInTransaction {

                    val movieVideosList: List<MovieVideosResponse.MovieVideo>  =item.results
                    for(movieVideo in movieVideosList)
                    {
                        movieVideo.movie_id = movie_id

                    }
                    tmdbVideosDao.insertMovieVideosList(movieVideosList) }
            }

        }.asLiveData()
    }
    fun loadVideosForTvShow(tv_show_id: Int): LiveData<Resource<List<TvShowVideoResponse.TvShowVideo>>> {
        return object : NetworkBoundResource<TvShowVideoResponse, List<TvShowVideoResponse.TvShowVideo>>(executors) {

            override fun shouldFetch(data: List<TvShowVideoResponse.TvShowVideo>?): Boolean {
                return data!!.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<TvShowVideoResponse.TvShowVideo>> {
                return tmdbVideosDao.getTvShowVideos(tv_show_id)

            }

            override fun createCall(): LiveData<ApiResponse<TvShowVideoResponse>> {

                return theMovieDbApi.getLiveVideosForTvShow(tv_show_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: TvShowVideoResponse) {
                //this is executed on background thread
                Log.d("nikola", "movie videos.size: " + item.results.size)
                database.runInTransaction {

                    val tvShowVideosList: List<TvShowVideoResponse.TvShowVideo>  =item.results
                    for(tvShowVideo in tvShowVideosList)
                    {
                        tvShowVideo.tv_show_id = tv_show_id

                    }
                    tmdbVideosDao.insertTvShowVideosList(tvShowVideosList) }
            }

        }.asLiveData()
    }

    fun loadDetailsForMovie(movie_id: Int): LiveData<Resource<MovieDetailsResponse>> {
        return object : NetworkBoundResource<MovieDetailsResponse, MovieDetailsResponse>(executors) {

            override fun shouldFetch(data: MovieDetailsResponse?): Boolean {
                 return data==null
            }

            override fun loadFromDb(): LiveData<MovieDetailsResponse> {

                return tmdbDetailsDao.getMovieDetails(movie_id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailsResponse>> {

                return theMovieDbApi.getLiveMovieDetails(movie_id,Constants.TMDB_API_KEY)

            }

            override fun saveCallResult(item: MovieDetailsResponse) {
                database.runInTransaction {
                    tmdbDetailsDao.insertMovieDetails(item)
                }
            }
        }.asLiveData()
    }
    fun loadDetailsForTvShow(tv_show_id: Int): LiveData<Resource<TvShowDetailsResponse>> {
        return object : NetworkBoundResource<TvShowDetailsResponse, TvShowDetailsResponse>(executors) {

            override fun shouldFetch(data: TvShowDetailsResponse?): Boolean {
                return data==null
            }

            override fun loadFromDb(): LiveData<TvShowDetailsResponse> {

                return tmdbDetailsDao.getTvShowDetails(tv_show_id)
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailsResponse>> {

                return theMovieDbApi.getLivetvShowDetails(tv_show_id,Constants.TMDB_API_KEY)

            }

            override fun saveCallResult(item: TvShowDetailsResponse) {
                database.runInTransaction {
                    tmdbDetailsDao.insertTvShowDetails(item)
                }
            }
        }.asLiveData()
    }

    fun getMovieFavourites(movieId: Int): LiveData<MovieFavourite>{
        //Checking if already added to favourite
        return favouritesDao.checkIfFavourite(movieId)
    }

    fun insertFavouriteMovie(movie: MovieFavourite){

        val runnable = Runnable {
           favouritesDao.insertFavourite(movie)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)

    }

    fun deleteFavouriteMovie(movie: MovieFavourite){

        val runnable = Runnable {
            favouritesDao.deleteFavourite(movie)
        }
        val diskRunnable = Runnable { database.runInTransaction(runnable) }
        executors.diskIO().execute(diskRunnable)

    }

}
