package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.credits.MovieCredits;

@Dao
public interface CreditsDao {

    // @Query("SELECT * FROM `Cast` WHERE titleId = :titleId ORDER BY `order` ASC")
    // LiveData<List<Cast>> getTitleCast(long titleId);

    // @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    //public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);

    @Query("SELECT * FROM movie_credits WHERE id = :movie_id LIMIT 1")
    LiveData<MovieCredits> getMovieCredits(Integer movie_id);
}
