package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.aero51.moviedatabase.repository.model.credits.Actor;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.credits.Crew;
import com.aero51.moviedatabase.repository.model.credits.MovieCredits;


import java.util.List;

@Dao
public abstract class CreditsDao {


    // @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    //public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);

   public  void insertCredits(MovieCredits movieCreditsRaw){
       insertMovieCredits(movieCreditsRaw);
       List<Cast> castList=movieCreditsRaw.getCast();
       for(int i=0;i<castList.size();i++){
           castList.get(i).setMovie_id(movieCreditsRaw.getId());
       }
       insertCastList(castList);

       List<Crew> crewList=movieCreditsRaw.getCrew();
       for(int i=0;i<crewList.size();i++){
           crewList.get(i).setMovie_id(movieCreditsRaw.getId());
       }
       insertCrewList(crewList);
   }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCastList(List<Cast> castList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCrewList(List<Crew> crewList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   public abstract void insertMovieCredits(MovieCredits movieCredits);

    @Query("SELECT * FROM movie_credit WHERE id = :movie_id LIMIT 1")
    public abstract MovieCredits getMovieCredits(Integer movie_id);

    @Query("SELECT * FROM movie_credit WHERE id = :movie_id LIMIT 1")
    public abstract LiveData<MovieCredits> getLiveMovieCredits(Integer movie_id);

    @Query("SELECT * FROM `Cast` WHERE movie_id = :movie_id ORDER BY `order` ASC")
    public  abstract LiveData<List<Cast>> getTitleCast(Integer movie_id);

    @Query("SELECT * FROM `Crew` WHERE movie_id = :movie_id ORDER BY `id` ASC")
    public abstract List<Crew> getTitleCrew(Integer movie_id);

    @Query("SELECT * FROM actor WHERE id = :id LIMIT 1")
    public  abstract LiveData<Actor> getActor(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActor(Actor actor);

}
