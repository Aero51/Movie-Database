package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.EpgTvRepository;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class EpgTvViewModel extends AndroidViewModel {
    private EpgTvRepository epgTvRepository;

    private LiveData<Resource<List<EpgProgram>>> resourceLiveData;

    public EpgTvViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        epgTvRepository = new EpgTvRepository(application, executors);
    }

    public LiveData<Resource<List<EpgChannel>>> getChannels() {
        return epgTvRepository.loadChannels();
    }

    public LiveData<Resource<List<EpgProgram>>> getProgramsForChannel(String channelName) {
        resourceLiveData = epgTvRepository.loadProgramsForChannel(channelName);
        return resourceLiveData;
    }

    public LiveData<Resource<List<EpgProgram>>> getResourceLiveData() {
        return resourceLiveData;
    }


}
