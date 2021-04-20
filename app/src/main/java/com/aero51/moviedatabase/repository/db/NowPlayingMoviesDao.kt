package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.room.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage

@Dao
interface NowPlayingMoviesDao {
    /**
     * Get the now playing Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */
    @Insert
    fun insert(now_playing_result: NowPlayingMovie)

    //@Transaction
    @Insert
    fun insertList(now_playing_results: List<NowPlayingMovie>)

    @Update
    fun update(now_playing_result: NowPlayingMovie)

    @Delete
    fun delete(now_playing_result: NowPlayingMovie)

    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @get:Query("SELECT * FROM now_playing_movie")
    val allResultsLiveData: LiveData<List<NowPlayingMovie>>

    // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @get:Query("SELECT * FROM now_playing_movie")
    val allResults: DataSource.Factory<Int, NowPlayingMovie>

    // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
    // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();
    @get:Query("SELECT * FROM now_playing_movies_page LIMIT 1")
    val moviePage: NowPlayingMoviesPage

    @get:Query("SELECT * FROM now_playing_movies_page LIMIT 1")
    val liveDataMoviePage: LiveData<NowPlayingMoviesPage>

    @Query("DELETE FROM now_playing_movies_page")
    fun deleteAllMoviePages()

    @Insert
    fun insertMoviePage(nowPlayingMoviesPage: NowPlayingMoviesPage)

    @get:Query("SELECT * FROM now_playing_movie")
    val alltestResultsNew: PositionalDataSource<NowPlayingMovie>

    @Query("SELECT * FROM now_playing_movie  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstNowPlayingMovie(): UpcomingMoviesPage.UpcomingMovie

    @Query("DELETE FROM now_playing_movies_page")
    suspend fun deleteAllNowPlayingMoviesPagesSuspended()

    @Query("DELETE FROM now_playing_movie ")
    suspend fun deleteAllNowPlayingMoviesSuspended()
}