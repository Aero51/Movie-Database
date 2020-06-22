package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.credits.MovieCredits;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;

public class CastNetworkBoundResource extends NetworkBoundResource<MovieCredits, List<Cast>> {
    private Integer movie_id;
    private MoviesDatabase database;
    private CreditsDao creditsDao;

    public CastNetworkBoundResource(AppExecutors appExecutors, Application application, Integer movie_id) {
        super(appExecutors);
        database = MoviesDatabase.getInstance(application);
        creditsDao = database.get_credits_dao();
        this.movie_id = movie_id;
    }


    @Override
    protected void saveCallResult(@NonNull MovieCredits item) {
        //this is executed on background thread
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                creditsDao.insertCredits(item);
            }
        });
        Log.d("moviedatabaselog", "saveCallResult movie id: " + item.getId() + " ,cast size: " + item.getCast().size());
    }

    @Override
    protected boolean shouldFetch(@Nullable List<Cast> data) {
        return data.size() == 0;
    }

    @NonNull
    @Override
    protected LiveData<List<Cast>> loadFromDb() {
        Log.d("moviedatabaselog", "loadFromDb movie_id: "+movie_id);
        return creditsDao.getTitleCast(movie_id);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<MovieCredits>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        return theMovieDbApi.getLiveMovieCredits(movie_id, API_KEY);
    }
}
