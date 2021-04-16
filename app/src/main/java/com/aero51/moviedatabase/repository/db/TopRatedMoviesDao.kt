package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.room.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage.TopRatedMovie
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowsByGenrePage

@Dao
interface TopRatedMoviesDao {
    /**
     * Get the top rated Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */
    @Insert
    fun insert(top_rated_result: TopRatedMovie)

    //@Transaction
    @Insert
    fun insertList(top_rated_results: List<TopRatedMovie>)

    @Update
    fun update(top_rated_result: TopRatedMovie)

    @Delete
    fun delete(top_rated_result: TopRatedMovie)

    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @get:Query("SELECT * FROM top_rated_movie")
    val allResultsLiveData: LiveData<List<TopRatedMovie>>

    // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @get:Query("SELECT * FROM top_rated_movie")
    val allResults: DataSource.Factory<Int, TopRatedMovie>

    // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
    // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();
    @get:Query("SELECT * FROM top_rated_movies_page LIMIT 1")
    val moviePage: TopRatedMoviesPage

    @get:Query("SELECT * FROM top_rated_movies_page LIMIT 1")
    val liveDataMoviePage: LiveData<TopRatedMoviesPage>

    @Query("DELETE FROM top_rated_movies_page")
    fun deleteAllMoviePages()

    @Insert
    fun insertMoviePage(top_rated_movies_page: TopRatedMoviesPage)

    @get:Query("SELECT * FROM top_rated_movie")
    val alltestResultsNew: PositionalDataSource<TopRatedMovie>

    @Query("SELECT * FROM top_rated_movie  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstTopRatedMovie(): TopRatedMovie

    @Query("DELETE FROM top_rated_movies_page")
    suspend fun deleteAllTopRatedMoviesPagesSuspended()

    @Query("DELETE FROM top_rated_movie ")
    suspend fun deleteAllTopRatedMoviesSuspended()
}