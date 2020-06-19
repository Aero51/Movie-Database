package com.aero51.moviedatabase.repository.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.credits.Crew;
import com.aero51.moviedatabase.repository.model.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.Converters;

import static com.aero51.moviedatabase.utils.Constants.DATABASE_NAME;

@Database(entities = {TopRatedMovie.class, TopRatedMoviesPage.class, PopularMovie.class, PopularMoviesPage.class, MovieCredits.class, Cast.class, Crew.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;

    public abstract TopRatedMoviesDao get_top_rated_movies_dao();
    public abstract PopularMoviesDao get_popular_movies_dao();
    public abstract CreditsDao get_credits_dao();


    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };




}
