package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage.AiringTvShow

@Dao
interface AiringTvShowsDao {
    /**
     * Get the airing Tv shows from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the  airing  tv shows from the table
     */
    //@Transaction
    @Insert
    fun insertList(popular_results: List<AiringTvShow>)

    @get:Query("SELECT * FROM airing_tv_show")
    val allResults: DataSource.Factory<Int, AiringTvShow>

    @get:Query("SELECT * FROM airing_tv_shows_page LIMIT 1")
    val tvShowPage: AiringTvShowsPage

    @get:Query("SELECT * FROM airing_tv_shows_page LIMIT 1")
    val liveDataAiringTvShowPage: LiveData<AiringTvShowsPage>

    @Query("DELETE FROM airing_tv_shows_page")
    fun deleteAllAiringTvShowPages()

    @Insert
    fun insertAiringTvShowPage(airing_tv_show_page: AiringTvShowsPage)


    @Query("SELECT * FROM airing_tv_show  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstAiringTvShow(): AiringTvShow

    @Query("DELETE FROM airing_tv_shows_page")
    suspend fun deleteAllAiringTvShowsPagesSuspended()

    @Query("DELETE FROM airing_tv_show ")
    suspend fun deleteAllAiringTvShowsSuspended()
}