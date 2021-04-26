package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM movie_favourite ORDER BY db_id ASC")
    fun loadAllFavourites(): LiveData<List<MovieFavourite>>

    @Query("SELECT * FROM movie_favourite WHERE id = :movieId   LIMIT 1")
    fun checkIfFavourite(movieId: Int):LiveData<MovieFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouritesEntry: MovieFavourite)

    @Delete
    fun deleteFavourite(movieFavourite: MovieFavourite)

    //@Query("SELECT COUNT(movieId) FROM favourites")
    //fun getNumberOfRows(): Int
}