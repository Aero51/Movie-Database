package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.GenresDao;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class TvShowGenresNetworkBoundresource  extends NetworkBoundResource<TvShowGenresResponse, List<TvShowGenresResponse.TvShowGenre>> {
    private Database database;
    private GenresDao genresDao;


    public TvShowGenresNetworkBoundresource(AppExecutors appExecutors, Application application) {
        super(appExecutors);
        database = Database.getInstance(application);
        genresDao = database.get_genres_dao();
    }

    @Override
    protected void saveCallResult(@NonNull TvShowGenresResponse item) {
        //this is executed on background thread
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                genresDao.insertTvShowGenreList(item.getGenres());
            }
        });

    }

    @Override
    protected boolean shouldFetch(@Nullable List<TvShowGenresResponse.TvShowGenre> data) {
        return data.size() == 0;
    }

    @NonNull
    @Override
    protected LiveData<List<TvShowGenresResponse.TvShowGenre>> loadFromDb() {
        return genresDao.getTvShowsGenres();
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<TvShowGenresResponse>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        return theMovieDbApi.getLiveTvGenres(TMDB_API_KEY);
    }
}
