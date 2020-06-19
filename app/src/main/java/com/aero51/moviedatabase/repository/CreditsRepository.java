package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;

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
        database = MoviesDatabase.getInstanceAllowOnMainThread(application);
        dao = database.get_credits_dao();
        this.executors = executors;

    }

    public LiveData<Resource<MovieCredits>> loadMovieCredits(Integer movie_id) {
        Log.d("moviedatabaselog", "loadMovieCredits id: " + movie_id);
        return new NetworkBoundResource<MovieCredits, MovieCredits>(executors) {
            @Override
            protected void saveCallResult(@NonNull MovieCredits item) {
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        dao.insertCredits(item);
                    }
                });
                Log.d("moviedatabaselog", "saveCallResult id: " + item.getId() + " ,cast size: " + item.getCast().size());
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieCredits data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<MovieCredits> loadFromDb() {
                MutableLiveData<MovieCredits> data = new MutableLiveData<>();
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        MovieCredits credits = dao.getMovieCredits(movie_id);
                        if (credits != null) {
                            credits.setCast(dao.getTitleCast(movie_id));
                            credits.setCrew(dao.getTitleCrew(movie_id));
                            data.setValue(credits);
                        }
                    }
                });

                return data;
                // return dao.getLiveMovieCredits(movie_id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieCredits>> createCall() {
                TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
                return theMovieDbApi.getLiveMovieCredits(movie_id, API_KEY);
            }
        }.asLiveData();

    }


}
