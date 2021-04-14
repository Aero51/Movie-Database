package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.EpgTvRepository;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

public class EpgDetailsViewModel extends AndroidViewModel {
    private EpgTvRepository epgTvRepository;
    private LiveData<Resource<ActorSearchResponse.ActorSearch>> liveActorSearchResult;


    public EpgDetailsViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        epgTvRepository = new EpgTvRepository(application, executors);
    }

    public LiveData<Resource<ActorSearchResponse.ActorSearch>> getActorSearch(String actor_name){
       liveActorSearchResult=epgTvRepository.loadActorSearch(actor_name);
       return liveActorSearchResult;
    }


    public LiveData<Resource<ActorSearchResponse.ActorSearch>> getLiveActorSearchResult() {
        return liveActorSearchResult;
    }


}
