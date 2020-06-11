package com.aero51.moviedatabase.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PositionalDataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Top_Rated_Result_Dao {

    /**
     * Get the top rated Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we  return LiveData.
     * We  get update every time the database update.
     * We using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     *
     * @return the top rated  movies from the table
     */

    @Insert
    void insert(Top_Rated_Result top_rated_result);

    //@Transaction
    @Insert
    void insertList(List<Top_Rated_Result> top_rated_results);



    @Update
    void update(Top_Rated_Result top_rated_result);

    @Delete
    void delete(Top_Rated_Result top_rated_result);

    @Query("DELETE FROM Top_Rated_Result")
    void deleteAllNotes();

   // @Query("SELECT * FROM top_rated_result")
   // List<Top_Rated_Result> getTopRatedResultMovies();


    //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
    @Query("SELECT * FROM Top_Rated_Result")
    LiveData<List<Top_Rated_Result>> getAllResultsLiveData();



    @Query("SELECT * FROM Top_Rated_Result")
    DataSource.Factory<Integer, Top_Rated_Result> getAllResultsNew();



    @Query("SELECT * FROM top_rated_result")
    PositionalDataSource<Top_Rated_Result> getAllpiResultsNew();

}

