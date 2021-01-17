package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;

import java.util.List;

@Dao
public interface PopularMoviesDao {

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
    void insertList(List<PopularMoviesPage.PopularMovie> popular_results);

    @Query("SELECT * FROM popular_movie")
    DataSource.Factory<Integer, PopularMoviesPage.PopularMovie> getAllResults();


    @Query("SELECT * FROM popular_movies_page LIMIT 1")
    PopularMoviesPage getMoviePage();

    @Query("SELECT * FROM popular_movies_page LIMIT 1")
    LiveData<PopularMoviesPage> getLiveDataMoviePage();

    @Query("DELETE FROM popular_movies_page")
    void deleteAllMoviePages();

    @Insert
    void insertMoviePage(PopularMoviesPage popular_movies_page);



}
