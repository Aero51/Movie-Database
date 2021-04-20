package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage

@Dao
interface PopularMoviesDao {
    /**
     * Get the top popular Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the popular  movies from the table
     */
    //@Transaction
    @Insert
    fun insertList(popular_results: List<PopularMovie>)

    @get:Query("SELECT * FROM popular_movie")
    val allResults: DataSource.Factory<Int?, PopularMovie>

    @get:Query("SELECT * FROM popular_movies_page LIMIT 1")
    val moviePage: PopularMoviesPage

    @get:Query("SELECT * FROM popular_movies_page LIMIT 1")
    val liveDataMoviePage: LiveData<PopularMoviesPage>

    @Query("DELETE FROM popular_movies_page")
    fun deleteAllMoviePages()

    @Insert
    fun insertMoviePage(popular_movies_page: PopularMoviesPage)

    @Query("SELECT * FROM popular_movie  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstPopularMovie(): UpcomingMoviesPage.UpcomingMovie

    @Query("DELETE FROM popular_movies_page")
    suspend fun deleteAllPopularMoviesPagesSuspended()

    @Query("DELETE FROM popular_movie ")
    suspend fun deleteAllPopularMoviesSuspended()


}