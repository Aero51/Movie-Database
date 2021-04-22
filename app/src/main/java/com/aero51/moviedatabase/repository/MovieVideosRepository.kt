package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.MovieVideosDao
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class MovieVideosRepository(private val application: Application, private val executors: AppExecutors)
{
    private val database: Database
    private var movieVideosDao: MovieVideosDao

    init {
        database = Database.getInstance(application)
        movieVideosDao = database._movie_videos_dao
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
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
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

}