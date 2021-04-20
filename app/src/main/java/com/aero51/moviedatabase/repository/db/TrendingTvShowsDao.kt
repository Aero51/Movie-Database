package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage.TrendingTvShow

@Dao
interface TrendingTvShowsDao {
    /**
     * Get the trending Tv shows from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the  trending  tv shows from the table
     */
    //@Transaction
    @Insert
    fun insertList(trending_results: List<TrendingTvShow>)

    @get:Query("SELECT * FROM trending_tv_show")
    val allResults: DataSource.Factory<Int, TrendingTvShow>

    @get:Query("SELECT * FROM trending_tv_shows_page LIMIT 1")
    val tvShowPage: TrendingTvShowsPage

    @get:Query("SELECT * FROM trending_tv_shows_page LIMIT 1")
    val liveDataTvShowPage: LiveData<TrendingTvShowsPage>

    @Query("DELETE FROM trending_tv_shows_page")
    fun deleteAllTvShowPages()

    @Insert
    fun insertTvShowPage(trendingTvShowsPage: TrendingTvShowsPage)

    @Query("SELECT * FROM trending_tv_show  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstTrendingTvShow(): TrendingTvShow

    @Query("DELETE FROM trending_tv_shows_page")
    suspend fun deleteAllTrendingTvShowsPagesSuspended()

    @Query("DELETE FROM trending_tv_show ")
    suspend fun deleteAllTrendingTvShowsSuspended()

}