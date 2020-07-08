package com.aero51.moviedatabase.repository.networkonlynotused.test;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MoviesDao {

    //@Transaction
    @Insert
    void inserMoviesList(List<Movie> movies);

    @Query("SELECT * FROM movie WHERE movieTypeId=:movieTypeId")
   DataSource.Factory<Integer, Movie> getMovieResults(Integer movieTypeId);


    @Query("DELETE FROM movie")
    void deleteAllMovies();


    //@Query("SELECT * FROM `Crew` WHERE movie_id = :movie_id ORDER BY `id` ASC")
   // @Query("SELECT MAX(db_id) FROM movie WHERE page_number=1")
   // @Query("SELECT MAX(page_number) FROM movie WHERE movieTypeId=:movieTypeId")
   //Integer getLastPageNumber(Integer movieTypeId);

    @Query("SELECT  MAX(page) FROM top_rated_page")
    Integer getTopRatedPage();

    @Query("DELETE FROM top_rated_page")
    void deleteTopRatedMoviePage();

    @Insert
    void insertTopRatedPage(TopRatedPage page);

    @Query("SELECT MAX(page) FROM popular_page")
    Integer getPopularPage();

    @Query("DELETE FROM popular_page")
    void deletePopularMoviePage();

    @Insert
    void insertPopularPage(PopularPage page);


}
