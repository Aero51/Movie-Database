package com.aero51.moviedatabase.repository.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.repository.model.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.Converters;

@Database(entities = {TopRatedMovie.class, TopRatedMoviesPage.class, PopularMovie.class, PopularMoviesPage.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;

    public abstract TopRatedMoviesDao get_top_rated_movies_dao();
    public abstract PopularMoviesDao get_popular_movies_dao();

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, "results_database")
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
