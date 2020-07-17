package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.networkboundresources.EpgChannelsNetworkBoundResource;
import com.aero51.moviedatabase.repository.networkboundresources.EpgProgramsForChannelNetworkBoundResource;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class EpgTvRepository {
    private AppExecutors executors;
    private Application application;

    public EpgTvRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        this.application = application;
    }

    public LiveData<Resource<List<EpgChannel>>> loadChannels(){
        Log.d("moviedatabaselog", "EpgTvRepository loadChannels ");
        return new EpgChannelsNetworkBoundResource(executors,application).asLiveData();
    }



    public LiveData<Resource<List<EpgProgram>>> loadProgramsForChannel(String channelName){
        Log.d("moviedatabaselog", "EpgTvRepository load other channel programs: "+channelName);
        return new EpgProgramsForChannelNetworkBoundResource(executors,application,channelName).asLiveData();
    }


}
