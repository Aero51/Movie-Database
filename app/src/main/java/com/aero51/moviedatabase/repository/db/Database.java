package com.aero51.moviedatabase.repository.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImage;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Crew;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.Converters;

import static com.aero51.moviedatabase.utils.Constants.DATABASE_NAME;

@androidx.room.Database(entities = {TopRatedMovie.class, TopRatedMoviesPage.class, PopularMovie.class,
                      PopularMoviesPage.class, MovieCredits.class, Cast.class, Crew.class,
                      Actor.class, ActorImage.class, EpgChannel.class, EpgProgram.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    private static Database instance;
    private static Database instanceOnMainThread;

    public abstract TopRatedMoviesDao get_top_rated_movies_dao();
    public abstract PopularMoviesDao get_popular_movies_dao();
    public abstract CreditsDao get_credits_dao();
    public abstract EpgTvDao get_epg_tv_dao();



    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;

    }
    public static synchronized Database getInstanceAllowOnMainThread(Context context) {
        if (instanceOnMainThread == null) {
            instanceOnMainThread = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();

        }
        return instanceOnMainThread;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("moviedatabaselog","RoomDatabase.Callback roomCallback onCreate  ");

        }
    };




}
