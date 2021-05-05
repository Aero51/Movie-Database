package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse

@Dao
interface TmdbDetailsDao {


    @Query("SELECT * FROM movie_details WHERE id=:movie_id LIMIT 1")
    fun getMovieDetails(movie_id: Int): LiveData<MovieDetailsResponse>

    @Query("SELECT * FROM tv_show_details WHERE id=:tv_show_id LIMIT 1")
    fun getTvShowDetails(tv_show_id: Int): LiveData<TvShowDetailsResponse>


    @Insert
    fun insertMovieDetails(movieDetails: MovieDetailsResponse)

    @Insert
    fun insertTvShowDetails(tvShowDetails: TvShowDetailsResponse)
}