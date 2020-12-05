package com.aero51.moviedatabase.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.SingleLiveEvent;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<EpgProgram> liveEpgProgram = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchToEpgDetailsFragment = new SingleLiveEvent<>();
    private Integer epgIndex;

    private MutableLiveData<ChannelWithPrograms> liveChannelWithPrograms = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchToEpgAllProgramsFragment = new SingleLiveEvent<>();


    private MutableLiveData<EpgOtherChannel> liveEpgOtherChannel = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchOtherChannelDetailFragment = new SingleLiveEvent<>();
    private Integer otherChannelIndex;

    private MutableLiveData<TopRatedMovie> livetopRatedMovie = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchTopMovieFragments = new SingleLiveEvent<>();
    private Integer topRatedMovieIndex;

    private MutableLiveData<PopularMovie> livePopularMovie = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchPopularMovieFragments = new SingleLiveEvent<>();
    private Integer popularMovieIndex;

    private MutableLiveData<Cast> liveCast = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchActorFragment = new SingleLiveEvent<>();
    private Integer castIndex;


    private MutableLiveData<Boolean> hasEpgTvFragmentFinishedLoading = new MutableLiveData<>();


    public void changeToEpgDetailsFragment(Integer index, EpgProgram epgProgram) {
        this.epgIndex = index;
        liveEpgProgram.setValue(epgProgram);
        shouldSwitchToEpgDetailsFragment.setValue(true);

    }

    public LiveData<EpgProgram> getLiveDataProgram() {
        return liveEpgProgram;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchToEpgTvDetailsFragment() {
        return shouldSwitchToEpgDetailsFragment;
    }

    public void changeToEpgAllProgramsFragment(ChannelWithPrograms channelWithPrograms) {

        liveChannelWithPrograms.setValue(channelWithPrograms);
        shouldSwitchToEpgAllProgramsFragment.setValue(true);

    }

    public LiveData<ChannelWithPrograms> getLiveDataChannelWithPrograms() {
        return liveChannelWithPrograms;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchToEpgAllProgramsFragment() {
        return shouldSwitchToEpgAllProgramsFragment;
    }


    public void changeToTopRatedMovieFragment(Integer position, TopRatedMovie topRatedMovie) {
        Log.d(Constants.LOG, "changeToTopRatedMovieFragment topRatedMovieId: " + topRatedMovie.getId());
        this.topRatedMovieIndex = position;
        livetopRatedMovie.setValue(topRatedMovie);
        shouldSwitchTopMovieFragments.setValue(true);

    }

    public LiveData<TopRatedMovie> getLiveDataTopRatedMovie() {
        return livetopRatedMovie;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchTopRatedMovieFragment() {
        return shouldSwitchTopMovieFragments;
    }

    public void changeToPopularMovieFragment(Integer position, PopularMovie popularMovie) {
        this.popularMovieIndex = position;
        livePopularMovie.setValue(popularMovie);
        shouldSwitchPopularMovieFragments.setValue(true);

    }

    public LiveData<PopularMovie> getLiveDataPopularMovie() {
        return livePopularMovie;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchPopularMovieFragment() {
        return shouldSwitchPopularMovieFragments;
    }

    public void changeToActorFragment(Integer position, Cast cast) {
        this.castIndex = position;
        liveCast.setValue(cast);
        shouldSwitchActorFragment.setValue(true);

    }

    public LiveData<Cast> getLiveDataCast() {
        return liveCast;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchActorFragment() {
        return shouldSwitchActorFragment;
    }

    public void setHasEpgTvFragmentFinishedLoading(boolean hasEpgTvFragmentFinishedLoading) {
        this.hasEpgTvFragmentFinishedLoading.setValue(hasEpgTvFragmentFinishedLoading);
    }

    public LiveData<Boolean> getHasEpgTvFragmentFinishedLoading() {
        return hasEpgTvFragmentFinishedLoading;
    }
}
