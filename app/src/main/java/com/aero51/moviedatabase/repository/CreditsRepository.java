package com.aero51.moviedatabase.repository;

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
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;


public class CreditsRepository {
    private MoviesDatabase databaseCanQueryOnMainThread;
    private CreditsDao dao;
    private AppExecutors executors;



    public CreditsRepository(Application application, AppExecutors executors) {
        databaseCanQueryOnMainThread = MoviesDatabase.getInstanceAllowOnMainThread(application);
        dao = databaseCanQueryOnMainThread.get_credits_dao();
        this.executors = executors;

    }

    public LiveData<Resource<List<Cast>>> loadCastById(Integer movie_id) {
        Log.d("moviedatabaselog", "loadCastById id: " + movie_id);
        return new NetworkBoundResource<MovieCredits, List<Cast>>(executors) {
            @Override
            protected void saveCallResult(@NonNull MovieCredits item) {
                //this is executed on background thread
                databaseCanQueryOnMainThread.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        dao.insertCredits(item);
                    }
                });
                Log.d("moviedatabaselog", "saveCallResult id: " + item.getId() + " ,cast size: " + item.getCast().size());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Cast> data) {
                return data.size() == 0;
            }

            @NonNull
            @Override
            protected LiveData<List<Cast>> loadFromDb() {
                return  dao.getTitleCast(movie_id);
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
