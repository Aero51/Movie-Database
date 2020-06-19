package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;

import com.aero51.moviedatabase.repository.model.credits.MovieCredits;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;
import com.aero51.moviedatabase.utils.Resource;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;


public class CreditsRepository {
    private MoviesDatabase database;
    private CreditsDao dao;
    private AppExecutors executors;


    public CreditsRepository(Application application, AppExecutors executors) {
        database = MoviesDatabase.getInstance(application);
        dao = database.get_credits_dao();
        this.executors = executors;

    }

    public LiveData<Resource<MovieCredits>> loadMovieCredits(Integer movie_id) {
        return new NetworkBoundResource<MovieCredits, MovieCredits>(executors) {
            @Override
            protected void saveCallResult(@NonNull MovieCredits item) {
                Log.d("moviedatabaselog", "saveCallResult id: " + item.getId()+" ,cast size: "+item.getCast().size());
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieCredits data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<MovieCredits> loadFromDb() {

                return dao.getMovieCredits(movie_id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieCredits>> createCall() {
                TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
                return theMovieDbApi.getLiveMovieCredits(movie_id,API_KEY);
            }
        }.asLiveData();

    }


}
