package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse

@Dao
interface MovieDetailsDao {




    @Query("SELECT * FROM movie_details WHERE id=:movie_id LIMIT 1" )
    fun getMovieDetails(movie_id: Int): LiveData<MovieDetailsResponse>


    @Insert
    fun insertMovieDetails(movieDetails: MovieDetailsResponse)
}