package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage

@Dao
interface MovieVideosDao {

    @Query("SELECT * FROM movie_video WHERE movie_id=:movie_id ORDER BY db_id asc" )
    fun getMovieVideos(movie_id: Int): LiveData<List<MovieVideosResponse.MovieVideo>>


    @Insert
    fun insertMovieVideosList(movieVideosList: List<MovieVideosResponse.MovieVideo>)

}