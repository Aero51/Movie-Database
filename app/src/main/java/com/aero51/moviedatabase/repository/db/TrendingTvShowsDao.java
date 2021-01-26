package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage;

import java.util.List;

@Dao
public interface TrendingTvShowsDao {


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
    void insertList(List<TrendingTvShowsPage.TrendingTvShow> trending_results);

    @Query("SELECT * FROM trending_tv_show")
    DataSource.Factory<Integer, TrendingTvShowsPage.TrendingTvShow> getAllResults();


    @Query("SELECT * FROM trending_tv_shows_page LIMIT 1")
    TrendingTvShowsPage getTvShowPage();

    @Query("SELECT * FROM trending_tv_shows_page LIMIT 1")
    LiveData<TrendingTvShowsPage> getLiveDataTvShowPage();

    @Query("DELETE FROM trending_tv_shows_page")
    void deleteAllTvShowPages();

    @Insert
    void insertTvShowPage(TrendingTvShowsPage trendingTvShowsPage);


}
