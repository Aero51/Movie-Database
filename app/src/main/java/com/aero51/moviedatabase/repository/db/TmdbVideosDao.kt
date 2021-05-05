package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse

@Dao
interface TmdbVideosDao {

    @Query("SELECT * FROM movie_video WHERE movie_id=:movie_id ORDER BY db_id asc" )
    fun getMovieVideos(movie_id: Int): LiveData<List<MovieVideosResponse.MovieVideo>>

    @Query("SELECT * FROM tv_show_video WHERE tv_show_id=:tv_show_id ORDER BY db_id asc" )
    fun getTvShowVideos(tv_show_id: Int): LiveData<List<TvShowVideoResponse.TvShowVideo>>

    @Insert
    fun insertMovieVideosList(movieVideosList: List<MovieVideosResponse.MovieVideo>)

    @Insert
    fun insertTvShowVideosList(tvShowVideosList: List<TvShowVideoResponse.TvShowVideo>)

}