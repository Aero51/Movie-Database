package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.PopularMoviesPage;

import java.util.List;

@Dao
public interface PopularMoviesDao {

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

    //@Transaction
    @Insert
    void insertList(List<PopularMovie> popular_results);

    @Query("SELECT * FROM popular_movie")
    DataSource.Factory<Integer, PopularMovie> getAllResults();


    @Query("SELECT * FROM popular_movies_page LIMIT 1")
    PopularMoviesPage getMoviePage();

    @Query("SELECT * FROM popular_movies_page LIMIT 1")
    LiveData<PopularMoviesPage> getLiveDataMoviePage();

    @Query("DELETE FROM popular_movies_page")
    void deleteAllMoviePages();

    @Insert
    void insertMoviePage(PopularMoviesPage popular_movies_page);

   // @Query("SELECT * FROM `Cast` WHERE titleId = :titleId ORDER BY `order` ASC")
   // LiveData<List<Cast>> getTitleCast(long titleId);

   // @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    //public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);

}
