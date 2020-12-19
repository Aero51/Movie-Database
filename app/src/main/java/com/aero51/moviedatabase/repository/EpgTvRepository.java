package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.networkboundresources.ActorSearchNetworkBoundResource;
import com.aero51.moviedatabase.repository.networkboundresources.EpgProgramsForChannelNetworkBoundResource;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class EpgTvRepository {
    private AppExecutors executors;
    private Application application;

    public EpgTvRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        this.application = application;
    }


    public LiveData<Resource<List<EpgProgram>>> loadProgramsForChannel(String channelName){
        Log.d(Constants.LOG, "EpgTvRepository load other channel programs: "+channelName);
        return new EpgProgramsForChannelNetworkBoundResource(executors,application,channelName).asLiveData();
    }


    public LiveData<Resource<ActorSearchResponse.ActorSearch>> loadActorSearch(String actor_name){
        Log.d(Constants.LOG, "EpgTvRepository loadActorSearch: "+actor_name);
        return new ActorSearchNetworkBoundResource(executors,application,actor_name).asLiveData();
    }
}
