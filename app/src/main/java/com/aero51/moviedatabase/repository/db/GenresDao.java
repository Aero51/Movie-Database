package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
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
}
