package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aero51.moviedatabase.repository.CreditsRepository;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class MovieDetailsViewModel extends AndroidViewModel {
    final private MutableLiveData<TopRatedMovie> topRatedmovie;
    final private MutableLiveData<PopularMovie> popularmovie;
    private CreditsRepository creditsRepository;


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        topRatedmovie = new MutableLiveData<TopRatedMovie>();
        popularmovie = new MutableLiveData<PopularMovie>();
        creditsRepository = new CreditsRepository(application, executors);

    }




    public LiveData<Resource<List<Cast>>> getPopularMovieCast( Integer popular_movie_id) {
        return creditsRepository.loadCastById(popular_movie_id);
    }

    public LiveData<Resource<List<Cast>>> getTopRatedMovieCast(Integer top_rated_movie_id) {
        return creditsRepository.loadCastById(top_rated_movie_id);
    }

}