package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage.PopularTvShow

@Dao
interface PopularTvShowsDao {
    /**
     * Get the popular Tv shows from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the  popular  tv shows from the table
     */
    //@Transaction
    @Insert
    fun insertList(popular_results: List<PopularTvShow>)

    @get:Query("SELECT * FROM popular_tv_show")
    val allResults: DataSource.Factory<Int, PopularTvShow>

    @get:Query("SELECT * FROM popular_tv_shows_page LIMIT 1")
    val tvShowPage: PopularTvShowsPage

    @get:Query("SELECT * FROM popular_tv_shows_page LIMIT 1")
    val liveDataTvShowPage: LiveData<PopularTvShowsPage>

    @Query("DELETE FROM popular_tv_shows_page")
    fun deleteAllTvShowPages()

    @Insert
    fun insertTvShowPage(popular_tv_show_page: PopularTvShowsPage)


    @Query("SELECT * FROM popular_tv_show  ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstPopularTvShow(): PopularTvShow

    @Query("DELETE FROM popular_tv_shows_page")
    suspend fun deleteAllPopularTvShowsPagesSuspended()

    @Query("DELETE FROM popular_tv_show ")
    suspend fun deleteAllPopularTvShowsSuspended()

}