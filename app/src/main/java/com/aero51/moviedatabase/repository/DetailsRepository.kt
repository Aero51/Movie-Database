package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.FavouritesDao
import com.aero51.moviedatabase.repository.db.MovieDetailsDao
import com.aero51.moviedatabase.repository.db.MovieVideosDao
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class DetailsRepository(application: Application, private val executors: AppExecutors) {


    private val database: Database = Database.getInstance(application)
    private var movieVideosDao: MovieVideosDao
    private var movieDetailsDao: MovieDetailsDao
    private val theMovieDbApi = RetrofitInstance.getTmdbApiService()
    private lateinit var favouritesDao: FavouritesDao

    init {
        movieVideosDao = database._movie_videos_dao
        movieDetailsDao=database._movie_details_dao
        favouritesDao=database._favourites_dao
    }

    fun loadVideosForMovie(movie_id: Int): LiveData<Resource<List<MovieVideosResponse.MovieVideo>>> {
        return object : NetworkBoundResource<MovieVideosResponse, List<MovieVideosResponse.MovieVideo>>(executors) {

            override fun shouldFetch(data: List<MovieVideosResponse.MovieVideo>?): Boolean {
                return data!!.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<MovieVideosResponse.MovieVideo>> {
                return movieVideosDao.getMovieVideos(movie_id)

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
                    movieVideosDao.insertMovieVideosList(movieVideosList) }
            }

        }.asLiveData()
    }


    fun loadDetailsForMovie(movie_id: Int): LiveData<Resource<MovieDetailsResponse>> {
        return object : NetworkBoundResource<MovieDetailsResponse, MovieDetailsResponse>(executors) {

            override fun shouldFetch(data: MovieDetailsResponse?): Boolean {
                 return data==null
            }

            override fun loadFromDb(): LiveData<MovieDetailsResponse> {

                return movieDetailsDao.getMovieDetails(movie_id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailsResponse>> {

                return theMovieDbApi.getLiveMovieDetails(movie_id,Constants.TMDB_API_KEY)

            }

            override fun saveCallResult(item: MovieDetailsResponse) {
                database.runInTransaction {
                    movieDetailsDao.insertMovieDetails(item)
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
