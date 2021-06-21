package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowFavourite

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM movie_favourite ORDER BY db_id ASC")
    fun loadAllMovieFavourites(): LiveData<List<MovieFavourite>>

    @Query("SELECT * FROM movie_favourite WHERE id = :movieId   LIMIT 1")
    fun checkIfMovieFavourite(movieId: Int):LiveData<MovieFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieFavourite(favouritesEntry: MovieFavourite)

    @Delete
    fun deleteMovieFavourite(movieFavourite: MovieFavourite)

    //@Query("SELECT COUNT(movieId) FROM favourites")
    //fun getNumberOfRows(): Int

    @Query("SELECT * FROM tv_show_favourite ORDER BY db_id ASC")
    fun loadAllTvShowFavourites(): LiveData<List<TvShowFavourite>>

    @Query("SELECT * FROM tv_show_favourite WHERE id = :tvShowId   LIMIT 1")
    fun checkIfTvShowFavourite(tvShowId: Int):LiveData<TvShowFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowFavourite(favouritesEntry: TvShowFavourite)

    @Delete
    fun deleteTvShowFavourite(tvShowFavourite: TvShowFavourite)

}