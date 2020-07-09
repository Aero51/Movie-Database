package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.SingleLiveEvent;

public class SharedViewModel extends ViewModel {

private MutableLiveData<EpgProgram> liveEpgProgram=new MutableLiveData<>();
private SingleLiveEvent<Boolean> shouldSwitchEpgFragments = new SingleLiveEvent<>();
private Integer epgIndex;

    private MutableLiveData<TopRatedMovie> livetopRatedMovie=new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchTopMovieFragments = new SingleLiveEvent<>();
    private Integer topRatedMovieIndex;

    private MutableLiveData<PopularMovie> livePopularMovie=new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchPopularMovieFragments = new SingleLiveEvent<>();
    private Integer popularMovieIndex;



public void ChangeEpgTvFragment(Integer index,EpgProgram epgProgram){
    this.epgIndex =index;
    shouldSwitchEpgFragments.setValue(true);
    liveEpgProgram.setValue(epgProgram);
}

public LiveData<EpgProgram> getLiveDataProgram(){
    return liveEpgProgram;
}
public LiveData<Boolean> getSingleLiveShouldSwitchEpgFragments(){
    return shouldSwitchEpgFragments;
}


    public void changeTopRatedMovieFragment(Integer position, TopRatedMovie topRatedMovie){
        this.topRatedMovieIndex= position;
        shouldSwitchTopMovieFragments.setValue(true);
        livetopRatedMovie.setValue(topRatedMovie);
    }

    public LiveData<TopRatedMovie> getLiveDataTopRatedMovie(){
        return livetopRatedMovie;
    }
    public LiveData<Boolean> getSingleLiveShouldSwitchTopRatedMovieFragment(){
        return shouldSwitchTopMovieFragments;
    }

    public void changePopularMovieFragment(Integer position, PopularMovie popularMovie){
        this.popularMovieIndex= position;
        shouldSwitchPopularMovieFragments.setValue(true);
        livePopularMovie.setValue(popularMovie);
    }

    public LiveData<PopularMovie> getLiveDataPopularMovie(){
        return livePopularMovie;
    }
    public LiveData<Boolean> getSingleLiveShouldSwitchPopularMovieFragment(){
        return shouldSwitchPopularMovieFragments;
    }
}
