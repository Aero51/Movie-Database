package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.GenresDao;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class MovieGenresNetworkBoundResource  extends NetworkBoundResource<MovieGenresResponse, List<MovieGenresResponse.MovieGenre>> {
    private Database database;
    private GenresDao genresDao;

    public MovieGenresNetworkBoundResource(AppExecutors appExecutors, Application application) {
        super(appExecutors);
        database = Database.getInstance(application);
        genresDao = database.get_genres_dao();
    }


    @Override
    protected void saveCallResult(@NonNull MovieGenresResponse item) {
        //this is executed on background thread
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                genresDao.insertGenreList(item.getGenres());
            }
        });

    }

    @Override
    protected boolean shouldFetch(@Nullable List<MovieGenresResponse.MovieGenre> data) {
        return data.size() == 0;
    }

    @NonNull
    @Override
    protected LiveData<List<MovieGenresResponse.MovieGenre>> loadFromDb() {
        return genresDao.getMoviesGenres();
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<MovieGenresResponse>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        return theMovieDbApi.getLiveMovieGenres(TMDB_API_KEY);
    }
}
