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

    private Integer top_rated_movie_id;
    private Integer popular_movie_id;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        topRatedmovie = new MutableLiveData<TopRatedMovie>();
        popularmovie = new MutableLiveData<PopularMovie>();
        creditsRepository = new CreditsRepository(application, executors);

    }


    public void selectTopRatedMovie(TopRatedMovie item) {
        top_rated_movie_id = item.getId();
        topRatedmovie.setValue(item);
    }

    public void selectPopularMovie(PopularMovie item) {
        popular_movie_id=item.getId();
        popularmovie.setValue(item);
    }

    public MutableLiveData<TopRatedMovie> getTopRatedMovie() {
        return topRatedmovie;
    }

    public MutableLiveData<PopularMovie> getPopularMovie() {
        return popularmovie;
    }

    public LiveData<Resource<List<Cast>>> getPopularMovieCast() {
        //setting movie_id before this method gets called by adding observer which would otherwise send movie_id as null
        return creditsRepository.loadCastById(popular_movie_id);
    }

    public LiveData<Resource<List<Cast>>> getTopRatedMovieCast() {
        //setting movie_id before this method gets called by adding observer which would otherwise send movie_id as null
        return creditsRepository.loadCastById(top_rated_movie_id);
    }

}