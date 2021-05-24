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
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson;
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowCredits;
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowsWithPerson;


import java.util.List;
import java.util.Objects;

@Dao
public abstract class CreditsDao {


    // @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    //public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);


    //TODO remove tv show credits and movie credits cast and crew ignore and add type converter
    public void insertMovCredits(MovieCredits movieCreditsRaw) {
        insertMovieCredits(movieCreditsRaw);
        List<MovieCredits.MovieCast> movieCastList = movieCreditsRaw.getMovieCast();
        for (int i = 0; i < movieCastList.size(); i++) {
            movieCastList.get(i).setMovie_id(movieCreditsRaw.getId());
        }
        insertMovieCastList(movieCastList);

        List<MovieCredits.MovieCrew> movieCrewList = movieCreditsRaw.getMovieCrew();
        for (int i = 0; i < movieCrewList.size(); i++) {
            movieCrewList.get(i).setMovie_id(movieCreditsRaw.getId());
        }
        insertMovieCrewList(movieCrewList);
    }

    public void insertTvCredits(TvShowCredits tvShowCreditsRaw) {
        insertTvShowCredits(tvShowCreditsRaw);
        List<TvShowCredits.TvShowCast> tvShowCastList = tvShowCreditsRaw.getTvShowCast();
        for (int i = 0; i < tvShowCastList.size(); i++) {
            tvShowCastList.get(i).setTv_show_id(tvShowCreditsRaw.getId());
        }
        insertTvShowCastList(tvShowCastList);

        List<TvShowCredits.TvShowCrew> tvShowCrewList = tvShowCreditsRaw.getTvShowCrew();
        for (int i = 0; i < tvShowCrewList.size(); i++) {
            tvShowCrewList.get(i).setTv_show_id(tvShowCreditsRaw.getId());
        }
        insertTvShowCrewList(tvShowCrewList);
    }

    public void insertActorImagesResponse(ActorImagesResponse response) {
        List<ActorImagesResponse.ActorImage> actorImageList = response.getImages();
        for (int i = 0; i < (actorImageList != null ? actorImageList.size() : 0); i++) {
            actorImageList.get(i).setActor_id(response.getId());
        }
        insertActorImages(actorImageList);
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovieCastList(List<MovieCredits.MovieCast> movieCastList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovieCrewList(List<MovieCredits.MovieCrew> movieCrewList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertMovieCredits(MovieCredits movieCredits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTvShowCastList(List<TvShowCredits.TvShowCast> movieCastList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTvShowCrewList(List<TvShowCredits.TvShowCrew> movieCrewList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertTvShowCredits(TvShowCredits tvShowCredits);

    @Query("SELECT * FROM movie_credits WHERE id = :movie_id LIMIT 1")
    public abstract MovieCredits getMovieCredits(Integer movie_id);

    @Query("SELECT * FROM movie_credits WHERE id = :movie_id LIMIT 1")
    public abstract LiveData<MovieCredits> getLiveMovieCredits(Integer movie_id);

    @Query("SELECT * FROM `movie_cast` WHERE movie_id =:movie_id ORDER BY `order` ASC")
    //"SELECT * FROM Movie WHERE id =:id"
    public abstract LiveData<List<MovieCredits.MovieCast>> getMovieCast(Integer movie_id);

    @Query("SELECT * FROM `movie_crew` WHERE movie_id =:movie_id ORDER BY `db_id` ASC")
    public abstract LiveData<List<MovieCredits.MovieCrew>> getMovieCrew(Integer movie_id);

    @Query("SELECT * FROM `tv_show_cast` WHERE tv_show_id =:tv_show_id ORDER BY `order` ASC")
    //"SELECT * FROM Movie WHERE id =:id"
    public abstract LiveData<List<TvShowCredits.TvShowCast>> getTvShowCast(Integer tv_show_id);

    @Query("SELECT * FROM `movie_crew` WHERE movie_id = :movie_id ORDER BY `id` ASC")
    public abstract List<MovieCredits.MovieCrew> getTitleCrew(Integer movie_id);

    @Query("SELECT * FROM actor WHERE id = :id LIMIT 1")
    public abstract LiveData<Actor> getActor(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActor(Actor actor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActorImages(List<ActorImagesResponse.ActorImage> imageList);

    @Query("SELECT * FROM actor_image WHERE actor_id = :actor_id ")
    public abstract LiveData<List<ActorImagesResponse.ActorImage>> getActorImages(Integer actor_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertActorSearch(ActorSearchResponse.ActorSearch actorSearch);

    @Query("SELECT * FROM actor_search WHERE name = :actorName LIMIT 1")
    public abstract LiveData<ActorSearchResponse.ActorSearch> getActorSearch(String actorName);

    @Query("SELECT * FROM movies_with_person WHERE id = :person_id LIMIT 1")
    public abstract LiveData<MoviesWithPerson> getMoviesWithPerson(Integer person_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMoviesWithPerson(MoviesWithPerson moviesWithPerson);

    @Query("SELECT * FROM tv_shows_with_person WHERE id = :person_id LIMIT 1")
    public abstract LiveData<TvShowsWithPerson> getTvShowsWithPerson(Integer person_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTvShowsWithPerson(TvShowsWithPerson tvShowsWithPerson);

}
