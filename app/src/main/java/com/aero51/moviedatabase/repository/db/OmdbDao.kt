package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel

@Dao
interface OmdbDao {


    @Insert
    fun insertOmdbMovieDetails(movie_details: OmdbModel)


    @Query("SELECT * FROM omdb_movie WHERE title=:movieTitle LIMIT 1")
    fun getLiveOmdbMovieByTitle(movieTitle: String): LiveData<OmdbModel>



}