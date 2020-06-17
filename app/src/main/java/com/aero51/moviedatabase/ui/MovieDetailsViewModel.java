package com.aero51.moviedatabase.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.Top_Rated_Result;

public class MovieDetailsViewModel extends ViewModel {
    final private MutableLiveData<Top_Rated_Result> movie = new MutableLiveData<Top_Rated_Result>();

    public void select(Top_Rated_Result item) {
        movie.setValue(item);
    }

    public MutableLiveData<Top_Rated_Result> getMovie() {
        return movie;
    }
}