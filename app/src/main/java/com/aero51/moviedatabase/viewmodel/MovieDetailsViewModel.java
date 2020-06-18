package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;

public class MovieDetailsViewModel extends ViewModel {
    final private MutableLiveData<TopRatedMovie> topRatedmovie = new MutableLiveData<TopRatedMovie>();
    final private MutableLiveData<PopularMovie> popularmovie = new MutableLiveData<PopularMovie>();

    public void selectTopRatedMovie(TopRatedMovie item) {
        topRatedmovie.setValue(item);
    }

    public void selectPopularMovie(PopularMovie item) {
        popularmovie.setValue(item);
    }

    public MutableLiveData<TopRatedMovie> getTopRatedMovie() {
        return topRatedmovie;
    }
    public MutableLiveData<PopularMovie> getPopularMovie() {
        return popularmovie;
    }

}