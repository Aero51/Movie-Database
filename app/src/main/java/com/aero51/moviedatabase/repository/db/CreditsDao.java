package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;


import java.util.List;

@Dao
public abstract class CreditsDao {


    // @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    //public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);

   public  void insertCredits(MovieCredits movieCreditsRaw){
       insertMovieCredits(movieCreditsRaw);
       List<MovieCredits.Cast> castList=movieCreditsRaw.getCast();
       for(int i=0;i<castList.size();i++){
           castList.get(i).setMovie_id(movieCreditsRaw.getId());
       }
       insertCastList(castList);

       List<MovieCredits.Crew> crewList=movieCreditsRaw.getCrew();
       for(int i=0;i<crewList.size();i++){
           crewList.get(i).setMovie_id(movieCreditsRaw.getId());
       }
       insertCrewList(crewList);
   }
    public  void insertActorImagesResponse(ActorImagesResponse response){
      List<ActorImagesResponse.ActorImage>  actorImageList= response.getImages();
        for(int i=0;i<actorImageList.size();i++){
          actorImageList.get(i).setActor_id(response.getId());
        }
        insertActorImages(actorImageList);
    }



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCastList(List<MovieCredits.Cast> castList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCrewList(List<MovieCredits.Crew> crewList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   public abstract void insertMovieCredits(MovieCredits movieCredits);

    @Query("SELECT * FROM movie_credit WHERE id = :movie_id LIMIT 1")
    public abstract MovieCredits getMovieCredits(Integer movie_id);

    @Query("SELECT * FROM movie_credit WHERE id = :movie_id LIMIT 1")
    public abstract LiveData<MovieCredits> getLiveMovieCredits(Integer movie_id);

    @Query("SELECT * FROM `cast` WHERE movie_id =:movie_id ORDER BY `order` ASC")   //"SELECT * FROM Movie WHERE id =:id"
    public  abstract LiveData<List<MovieCredits.Cast>> getTitleCast(Integer movie_id);

    @Query("SELECT * FROM `Crew` WHERE movie_id = :movie_id ORDER BY `id` ASC")
    public abstract List<MovieCredits.Crew> getTitleCrew(Integer movie_id);

    @Query("SELECT * FROM actor WHERE id = :id LIMIT 1")
    public  abstract LiveData<Actor> getActor(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActor(Actor actor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActorImages(List<ActorImagesResponse.ActorImage> imageList);

    @Query("SELECT * FROM actor_image WHERE actor_id = :actor_id ")
    public abstract LiveData<List<ActorImagesResponse.ActorImage>> getActorImages(Integer actor_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActorSearch(ActorSearchResponse.ActorSearch actorSearch);

    @Query("SELECT * FROM actor_search WHERE name = :actorName LIMIT 1")
    public abstract LiveData<ActorSearchResponse.ActorSearch>getActorSearch(String actorName);


}
