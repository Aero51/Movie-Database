package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;

import java.util.List;

@Dao
public interface GenresDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovieGenreList(List<MovieGenresResponse.MovieGenre> genreList);

    @Query("SELECT * FROM movie_genre")
    public abstract LiveData<List<MovieGenresResponse.MovieGenre>> getMoviesGenres();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTvShowGenreList(List<TvShowGenresResponse.TvShowGenre> genreList);

    @Query("SELECT * FROM movie_genre")
    public abstract LiveData<List<TvShowGenresResponse.TvShowGenre>> getTvShowsGenres();



    @Query("SELECT * FROM genre_movie WHERE  genreId=:genreId")
    DataSource.Factory<Integer, MoviesByGenrePage.GenreMovie>  getMoviesByGenre(Integer genreId);

    @Insert
    void insertList(List<MoviesByGenrePage.GenreMovie> moviesByGenre);

    @Query("SELECT * FROM movies_by_genre_page LIMIT 1")
    LiveData<MoviesByGenrePage> getLiveDataMoviesByGenrePage();

    @Insert
    void insertPage(MoviesByGenrePage page);

    @Query("DELETE FROM movies_by_genre_page")
    void deleteAllMoviesByGenrePages();

}
