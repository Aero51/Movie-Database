package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import java.util.List;

@Dao
public interface UpcomingMoviesDao {
    /**
     * Get the upcoming Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */

    @Insert
    void insert(UpcomingMovie top_rated_result);

    //@Transaction
    @Insert
    void insertList(List<UpcomingMovie> upcoming_results);

    @Update
    void update(UpcomingMovie upcoming_result);

    @Delete
    void delete(UpcomingMovie now_playing_result);


    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @Query("SELECT * FROM upcoming_movie")
    LiveData<List<UpcomingMovie>> getAllResultsLiveData();


    // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @Query("SELECT * FROM upcoming_movie")
    DataSource.Factory<Integer, UpcomingMovie> getAllResults();

    // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
    // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();

    @Query("SELECT * FROM upcoming_movies_page LIMIT 1")
    UpcomingMoviesPage getMoviePage();

    @Query("SELECT * FROM upcoming_movies_page LIMIT 1")
    LiveData<UpcomingMoviesPage> getLiveDataMoviePage();

    @Query("DELETE FROM upcoming_movies_page")
    void deleteAllMoviePages();

    @Insert
    void insertMoviePage(UpcomingMoviesPage upcomingMoviesPage);


    @Query("SELECT * FROM upcoming_movie")
    PositionalDataSource<UpcomingMovie> getAlltestResultsNew();

}
