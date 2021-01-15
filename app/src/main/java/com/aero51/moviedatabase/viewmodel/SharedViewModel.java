package com.aero51.moviedatabase.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.SingleLiveEvent;
import com.google.gson.Gson;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<EpgProgram> liveEpgProgram = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchToEpgDetailsFragment = new SingleLiveEvent<>();
    private Integer epgIndex;

    private MutableLiveData<ChannelWithPrograms> liveChannelWithPrograms = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchToEpgAllProgramsFragment = new SingleLiveEvent<>();


    private MutableLiveData<EpgOtherChannel> liveEpgOtherChannel = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchOtherChannelDetailFragment = new SingleLiveEvent<>();
    private Integer otherChannelIndex;


    private MutableLiveData<Movie> liveMovie = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchMovieDetailFragment = new SingleLiveEvent<>();
    private Integer movieIndex;


    private MutableLiveData<Integer> liveActorId = new MutableLiveData<>();
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

    //done like this to reduce code duplication(fragments, listeners, main activity identifiers
    public void changeToMoviedetailsFragment(Object movieObject,Integer position)
    {
        Movie movie = new Movie();
        if (movieObject instanceof TopRatedMovie)
        {
            TopRatedMovie topRatedMovie =(TopRatedMovie)movieObject;
            movie=transformTopRatedMovie(topRatedMovie);
        }
        if (movieObject instanceof PopularMovie){
            PopularMovie popularMovie=(PopularMovie) movieObject;
            movie=transformPopularMovie(popularMovie);
        }
        this.movieIndex=position;
        liveMovie.setValue(movie);
        shouldSwitchMovieDetailFragment.setValue(true);

    }

    public LiveData<Movie> getLiveDataMovie() {
        return liveMovie;
    }
    public LiveData<Boolean> getSingleLiveShouldSwitchMovieDetailsFragment() {
        return shouldSwitchMovieDetailFragment;
    }


    public void changeToActorFragment(Integer position, Integer actorId) {
        this.castIndex = position;
        liveActorId.setValue(actorId);
        shouldSwitchActorFragment.setValue(true);

    }

    public LiveData<Integer> getLiveDataActorId() {
        return liveActorId;
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


    //deserialization and searialization
    Movie transformTopRatedMovie (TopRatedMovie original) {
        Gson gson= new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }

    Movie transformPopularMovie (PopularMovie original) {
        Gson gson= new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }
}
