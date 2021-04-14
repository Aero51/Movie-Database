package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;

import java.util.List;

@Dao
public interface TopRatedMoviesDao {

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
    void insert(TopRatedMoviesPage.TopRatedMovie top_rated_result);

    //@Transaction
    @Insert
    void insertList(List<TopRatedMoviesPage.TopRatedMovie> top_rated_results);

    @Update
    void update(TopRatedMoviesPage.TopRatedMovie top_rated_result);

    @Delete
    void delete(TopRatedMoviesPage.TopRatedMovie top_rated_result);


    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @Query("SELECT * FROM top_rated_movie")
    LiveData<List<TopRatedMoviesPage.TopRatedMovie>> getAllResultsLiveData();


   // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @Query("SELECT * FROM top_rated_movie")
    DataSource.Factory<Integer, TopRatedMoviesPage.TopRatedMovie> getAllResults();

   // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
   // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();

   @Query("SELECT * FROM top_rated_movies_page LIMIT 1")
   TopRatedMoviesPage getMoviePage();

    @Query("SELECT * FROM top_rated_movies_page LIMIT 1")
    LiveData<TopRatedMoviesPage> getLiveDataMoviePage();

   @Query("DELETE FROM top_rated_movies_page")
    void deleteAllMoviePages();

    @Insert
    void insertMoviePage(TopRatedMoviesPage top_rated_movies_page);


    @Query("SELECT * FROM top_rated_movie")
    PositionalDataSource<TopRatedMoviesPage.TopRatedMovie> getAlltestResultsNew();

}

