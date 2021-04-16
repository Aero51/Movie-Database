package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
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

    private MutableLiveData<Integer> liveGenreId = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> shouldSwitchMoviesByGenreListFragment = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> shouldSwitchTvShowsByGenreListFragment = new SingleLiveEvent<>();


    private MutableLiveData<Boolean> hasEpgTvFragmentFinishedLoading = new MutableLiveData<>();

    public SharedViewModel() {
        //used when process is killed and tv shows fragment is selected, otherwise tv shows fragment would be empty on relaunch
        hasEpgTvFragmentFinishedLoading.setValue(true);
    }


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
    public void changeToMoviedetailsFragment(Object movieObject, Integer position) {
        Movie movie = new Movie();
        if (movieObject instanceof TopRatedMoviesPage.TopRatedMovie) {
            TopRatedMoviesPage.TopRatedMovie topRatedMovie = (TopRatedMoviesPage.TopRatedMovie) movieObject;
            movie = transformTopRatedMovie(topRatedMovie);
        }
        if (movieObject instanceof NowPlayingMoviesPage.NowPlayingMovie) {
            NowPlayingMoviesPage.NowPlayingMovie nowPlayingMovie = (NowPlayingMoviesPage.NowPlayingMovie) movieObject;
            movie = transformNowPlayingMovie(nowPlayingMovie);
        }
        if (movieObject instanceof PopularMoviesPage.PopularMovie) {
            PopularMoviesPage.PopularMovie popularMovie = (PopularMoviesPage.PopularMovie) movieObject;
            movie = transformPopularMovie(popularMovie);
        }
        if (movieObject instanceof UpcomingMoviesPage.UpcomingMovie) {
            UpcomingMoviesPage.UpcomingMovie upcomingMovie = (UpcomingMoviesPage.UpcomingMovie) movieObject;
            movie = transformUpcomingMovie(upcomingMovie);
        }
        this.movieIndex = position;
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

    public void changeToMoviesByGenreListFragment(Integer genreId, Integer position ) {

        liveGenreId.setValue(genreId);
        shouldSwitchMoviesByGenreListFragment.setValue(true);

    }
    public void changeToTvShowsByGenreListFragment(Integer genreId, Integer position ) {

        liveGenreId.setValue(genreId);
        shouldSwitchTvShowsByGenreListFragment.setValue(true);

    }

    public LiveData<Integer> getLiveDataActorId() {
        return liveActorId;
    }

    public LiveData<Boolean> getSingleLiveShouldSwitchActorFragment() {
        return shouldSwitchActorFragment;
    }
    public LiveData<Integer> getLiveDataGenreId() {
        return liveGenreId;
    }
    public LiveData<Boolean> getSingleLiveShouldSwitchMoviesByGenreListFragment() {
        return shouldSwitchMoviesByGenreListFragment;
    }
    public LiveData<Boolean> getSingleLiveShouldSwitchTvShowsByGenreListFragment() {
        return shouldSwitchTvShowsByGenreListFragment;
    }

    public void setHasEpgTvFragmentFinishedLoading(boolean hasEpgTvFragmentFinishedLoading) {
        this.hasEpgTvFragmentFinishedLoading.setValue(hasEpgTvFragmentFinishedLoading);
    }

    public LiveData<Boolean> getHasEpgTvFragmentFinishedLoading() {
        return hasEpgTvFragmentFinishedLoading;
    }


    //deserialization and serialization
    Movie transformTopRatedMovie(TopRatedMoviesPage.TopRatedMovie original) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }

    Movie transformNowPlayingMovie(NowPlayingMoviesPage.NowPlayingMovie original) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }

    Movie transformPopularMovie(PopularMoviesPage.PopularMovie original) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }

    Movie transformUpcomingMovie(UpcomingMoviesPage.UpcomingMovie original) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(original), Movie.class);
    }
}
