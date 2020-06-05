package com.aero51.moviedatabase.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface Top_Rated_Result_Dao {



        @Insert
        void insert(Top_Rated_Result top_rated_result);

        @Update
        void update(Top_Rated_Result top_rated_result);

        @Delete
        void delete(Top_Rated_Result top_rated_result);

        @Query("DELETE FROM Top_Rated_Result")
        void deleteAllNotes();

      //  @Query("SELECT * FROM note_table ORDER BY priority DESC")
      @Query("SELECT * FROM Top_Rated_Result")
        LiveData<List<Top_Rated_Result>> getAllResults();

    }

