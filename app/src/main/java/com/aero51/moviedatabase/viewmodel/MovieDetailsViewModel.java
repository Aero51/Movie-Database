package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.model.TopRatedMovie;

public class MovieDetailsViewModel extends ViewModel {
    final private MutableLiveData<TopRatedMovie> movie = new MutableLiveData<TopRatedMovie>();

    public void select(TopRatedMovie item) {
        movie.setValue(item);
    }

    public MutableLiveData<TopRatedMovie> getMovie() {
        return movie;
    }
}