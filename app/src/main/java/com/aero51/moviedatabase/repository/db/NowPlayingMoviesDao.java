package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;

import java.util.List;

@Dao
public interface NowPlayingMoviesDao {

    /**
     * Get the now playing Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We are using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */

    @Insert
    void insert(NowPlayingMoviesPage.NowPlayingMovie now_playing_result);

    //@Transaction
    @Insert
    void insertList(List<NowPlayingMoviesPage.NowPlayingMovie> now_playing_results);

    @Update
    void update(NowPlayingMoviesPage.NowPlayingMovie now_playing_result);

    @Delete
    void delete(NowPlayingMoviesPage.NowPlayingMovie now_playing_result);


    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @Query("SELECT * FROM now_playing_movie")
    LiveData<List<NowPlayingMoviesPage.NowPlayingMovie>> getAllResultsLiveData();


   // @Query("SELECT * FROM top_rated_movie WHERE CollectionsName=1")
    @Query("SELECT * FROM now_playing_movie")
    DataSource.Factory<Integer, NowPlayingMoviesPage.NowPlayingMovie> getAllResults();

   // @Query("SELECT * FROM top_rated_movie_page WHERE page= (SELECT MAX(page) FROM top_rated_movie_page)")
   // LiveData<Top_Rated_Movies_Page> getLatestMoviePage();

   @Query("SELECT * FROM now_playing_movies_page LIMIT 1")
   NowPlayingMoviesPage getMoviePage();

    @Query("SELECT * FROM now_playing_movies_page LIMIT 1")
    LiveData<NowPlayingMoviesPage> getLiveDataMoviePage();

   @Query("DELETE FROM now_playing_movies_page")
    void deleteAllMoviePages();

    @Insert
    void insertMoviePage(NowPlayingMoviesPage nowPlayingMoviesPage);


    @Query("SELECT * FROM now_playing_movie")
    PositionalDataSource<NowPlayingMoviesPage.NowPlayingMovie> getAlltestResultsNew();

}

