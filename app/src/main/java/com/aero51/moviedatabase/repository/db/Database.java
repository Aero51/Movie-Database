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
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Converters;

import static com.aero51.moviedatabase.utils.Constants.DATABASE_NAME;

@androidx.room.Database(entities = {TopRatedMoviesPage.class,TopRatedMoviesPage.TopRatedMovie.class,
        NowPlayingMoviesPage.NowPlayingMovie.class, NowPlayingMoviesPage.class,
        PopularMoviesPage.PopularMovie.class,
        PopularMoviesPage.class, UpcomingMoviesPage.UpcomingMovie.class, UpcomingMoviesPage.class,
        PopularTvShowsPage.class, PopularTvShowsPage.PopularTvShow.class,
        AiringTvShowsPage.class, AiringTvShowsPage.AiringTvShow.class,
        TrendingTvShowsPage.class, TrendingTvShowsPage.TrendingTvShow.class,
        MovieCredits.class, MovieCredits.Cast.class, MovieCredits.Crew.class, Actor.class,
        ActorImagesResponse.ActorImage.class, EpgChannel.class, EpgProgram.class, ActorSearchResponse.ActorSearch.class,
        MovieGenresResponse.MovieGenre.class, TvShowGenresResponse.TvShowGenre.class, MoviesByGenrePage.class,MoviesByGenrePage.GenreMovie.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    private static Database instance;
    private static Database instanceOnMainThread;

    public abstract TopRatedMoviesDao get_top_rated_movies_dao();
    public abstract PopularMoviesDao get_popular_movies_dao();
    public abstract NowPlayingMoviesDao get_now_playing_movies_dao();
    public abstract UpcomingMoviesDao get_upcoming_movies_dao();
    public abstract PopularTvShowsDao get_popular_tv_shows_dao();
    public abstract AiringTvShowsDao get_airing_tv_shows_dao();
    public abstract TrendingTvShowsDao get_trending_tv_shows_dao();
    public abstract GenresDao get_genres_dao();

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
            Log.d(Constants.LOG, "RoomDatabase.Callback roomCallback onCreate  ");

        }
    };


}
