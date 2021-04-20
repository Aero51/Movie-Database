package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.room.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie

@Dao
interface UpcomingMoviesDao {
    /**
     * Get the upcoming Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */
    @Insert
    fun insert(top_rated_result: UpcomingMovie)

    //@Transaction
    @Insert
    fun insertList(upcoming_results: List<UpcomingMovie>)

    @Update
    fun update(upcoming_result: UpcomingMovie)

    @Delete
    fun delete(now_playing_result: UpcomingMovie)

    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @get:Query("SELECT * FROM upcoming_movie")
    val allResultsLiveData: LiveData<List<UpcomingMovie>>

    // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @get:Query("SELECT * FROM upcoming_movie")
    val allResults: DataSource.Factory<Int, UpcomingMovie>

    // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
    // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();
    @get:Query("SELECT * FROM upcoming_movies_page LIMIT 1")
    val moviePage: UpcomingMoviesPage

    @get:Query("SELECT * FROM upcoming_movies_page LIMIT 1")
    val liveDataMoviePage: LiveData<UpcomingMoviesPage>

    @Query("DELETE FROM upcoming_movies_page")
    fun deleteAllMoviePages()

    @Insert
    fun insertMoviePage(upcomingMoviesPage: UpcomingMoviesPage)

    @get:Query("SELECT * FROM upcoming_movie")
    val alltestResultsNew: PositionalDataSource<UpcomingMovie>

    @Query("SELECT * FROM upcoming_movie  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstUpcomingMovie(): UpcomingMovie

    @Query("DELETE FROM upcoming_movies_page")
    suspend fun deleteAllUpcomingMoviesPagesSuspended()

    @Query("DELETE FROM upcoming_movie ")
    suspend fun deleteAllUpcomingMoviesSuspended()


}