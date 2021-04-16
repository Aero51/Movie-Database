package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage.MovieByGenre
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowsByGenrePage

@Dao
interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreList(genreList: List<MovieGenre>)

    @get:Query("SELECT * FROM movie_genre")
    val moviesGenres: LiveData<List<MovieGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowGenreList(genreList: List<TvShowGenre>)

    @get:Query("SELECT * FROM tv_show_genre")
    val tvShowsGenres: LiveData<List<TvShowGenre>>

    @Query("SELECT * FROM movie_by_genre WHERE  genreId=:genreId")
    fun getMoviesByGenre(genreId: Int): DataSource.Factory<Int, MovieByGenre>

    @Insert
    fun insertMoviesByGenreList(moviesByGenre: List<MovieByGenre>)

    @get:Query("SELECT * FROM movies_by_genre_page LIMIT 1")
    val liveDataMoviesByGenrePage: LiveData<MoviesByGenrePage>

    @Insert
    fun insertMoviesByGenrePage(page: MoviesByGenrePage)

    @Query("DELETE FROM movies_by_genre_page")
    fun deleteAllMoviesByGenrePages()

    @Query("DELETE FROM movies_by_genre_page")
    suspend fun deleteAllMoviesByGenrePagesSuspended()

    @Query("SELECT * FROM movie_by_genre WHERE  genreId=:genreId ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstMovieByGenre(genreId: Int): MovieByGenre

    @Query("DELETE FROM movie_by_genre WHERE genreId=:genreId ")
    suspend fun deleteAllMoviesByGenre(genreId: Int)




    @Query("SELECT * FROM tv_show_by_genre WHERE  genreId=:genreId")
    fun getTvShowsByGenre(genreId: Int): DataSource.Factory<Int, TvShowsByGenrePage.TvShowByGenre>

    @Insert
    fun insertTvShowsByGenreList(tvShowsByGenre: List<TvShowsByGenrePage.TvShowByGenre>)

    @get:Query("SELECT * FROM tv_shows_by_genre_page LIMIT 1")
    val liveDataTvShowsByGenrePage: LiveData<TvShowsByGenrePage>


    @Insert
    fun insertTvShowsByGenrePage(page: TvShowsByGenrePage)

    @Query("DELETE FROM tv_shows_by_genre_page")
    fun deleteAllTvShowsByGenrePages()

    @Query("DELETE FROM tv_shows_by_genre_page")
    suspend fun deleteAllTvShowsByGenrePagesSuspended()

    @Query("SELECT * FROM tv_show_by_genre WHERE  genreId=:genreId ORDER BY db_id ASC LIMIT 1")
    suspend fun getFirstTvShowByGenre(genreId: Int): TvShowsByGenrePage.TvShowByGenre

    @Query("DELETE FROM tv_show_by_genre WHERE genreId=:genreId ")
    suspend fun deleteAllTvShowsByGenre(genreId: Int)


}