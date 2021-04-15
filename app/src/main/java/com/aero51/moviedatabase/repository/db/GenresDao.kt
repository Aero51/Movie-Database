package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage.GenreMovie
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreList(genreList: List<MovieGenre>)

    @get:Query("SELECT * FROM movie_genre")
    val moviesGenres: LiveData<List<MovieGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowGenreList(genreList: List<TvShowGenre>)

    @get:Query("SELECT * FROM movie_genre")
    val tvShowsGenres: LiveData<List<TvShowGenre>>

    @Query("SELECT * FROM genre_movie WHERE  genreId=:genreId")
    fun getMoviesByGenre(genreId: Int): DataSource.Factory<Int, GenreMovie>

    @Insert
    fun insertList(moviesByGenre: List<GenreMovie>)

    @get:Query("SELECT * FROM movies_by_genre_page LIMIT 1")
    val liveDataMoviesByGenrePage: LiveData<MoviesByGenrePage>

    @Insert
    fun insertPage(page: MoviesByGenrePage)

    @Query("DELETE FROM movies_by_genre_page")
    fun deleteAllMoviesByGenrePages()

    @Query("DELETE FROM movies_by_genre_page")
    suspend fun deleteAllMoviesByGenrePagesSuspended()

    @Query("SELECT * FROM genre_movie WHERE  genreId=:genreId ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstMovieByGenre(genreId: Int): GenreMovie

    @Query("DELETE FROM genre_movie WHERE genreId=:genreId ")
    suspend fun deleteAllMoviesByGenre(genreId: Int)

}