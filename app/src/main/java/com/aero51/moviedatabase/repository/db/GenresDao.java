package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;

import java.util.List;

@Dao
public interface GenresDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertGenreList(List<MovieGenresResponse.MovieGenre> genreList);

    @Query("SELECT * FROM movie_genre")
    public abstract LiveData<List<MovieGenresResponse.MovieGenre>> getMoviesGenres();
}
