package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import java.util.List;
@Dao
public interface PopularTvShowsDao {


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
    void insertList(List<PopularTvShowsPage.PopularTvShow> popular_results);

    @Query("SELECT * FROM popular_tv_show")
    DataSource.Factory<Integer, PopularTvShowsPage.PopularTvShow> getAllResults();


    @Query("SELECT * FROM popular_tv_shows_page LIMIT 1")
    PopularTvShowsPage getTvShowPage();

    @Query("SELECT * FROM popular_tv_shows_page LIMIT 1")
    LiveData<PopularTvShowsPage> getLiveDataTvShowPage();

    @Query("DELETE FROM popular_tv_shows_page")
    void deleteAllTvShowPages();

    @Insert
    void insertTvShowPage(PopularTvShowsPage popular_tv_show_page);



}
