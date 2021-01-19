package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;

import java.util.List;
@Dao
public interface AiringTvShowsDao {


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
    void insertList(List<AiringTvShowsPage.AiringTvShow> popular_results);

    @Query("SELECT * FROM airing_tv_show")
    DataSource.Factory<Integer, AiringTvShowsPage.AiringTvShow> getAllResults();


    @Query("SELECT * FROM airing_tv_shows_page LIMIT 1")
    AiringTvShowsPage getTvShowPage();

    @Query("SELECT * FROM airing_tv_shows_page LIMIT 1")
    LiveData<AiringTvShowsPage> getLiveDataAiringTvShowPage();

    @Query("DELETE FROM airing_tv_shows_page")
    void deleteAllAiringTvShowPages();

    @Insert
    void insertAiringTvShowPage(AiringTvShowsPage airing_tv_show_page);

}
