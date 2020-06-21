package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;

import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.networkboundresources.CastNetworkBoundResource;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;


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
        return new CastNetworkBoundResource(executors, databaseCanQueryOnMainThread, movie_id).asLiveData();
    }


}
