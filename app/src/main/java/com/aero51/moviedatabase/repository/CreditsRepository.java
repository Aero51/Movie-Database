package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;

import com.aero51.moviedatabase.repository.model.credits.Actor;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.networkboundresources.ActorNetworkBoundResource;
import com.aero51.moviedatabase.repository.networkboundresources.CastNetworkBoundResource;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;


public class CreditsRepository {
    private AppExecutors executors;
private Application application;

    public CreditsRepository(Application application, AppExecutors executors) {
       // databaseCanQueryOnMainThread = MoviesDatabase.getInstanceAllowOnMainThread(application);
        this.executors = executors;
        this.application=application;

    }

    public LiveData<Resource<List<Cast>>> loadCastById(Integer movie_id) {
        Log.d("moviedatabaselog", "loadCastById id: " + movie_id);
        return new CastNetworkBoundResource(executors,application,  movie_id).asLiveData();
    }

    public LiveData<Resource<Actor>> loadActorById(Integer actor_id) {
        Log.d("moviedatabaselog", "loadActorById id: " + actor_id);
        return new ActorNetworkBoundResource(executors,application, actor_id).asLiveData();
    }

}
