package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aero51.moviedatabase.repository.CreditsRepository;
import com.aero51.moviedatabase.repository.model.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

public class MovieDetailsViewModel extends AndroidViewModel {
    final private MutableLiveData<TopRatedMovie> topRatedmovie ;
    final private MutableLiveData<PopularMovie> popularmovie ;
    private CreditsRepository creditsRepository;
    private AppExecutors executors;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        executors=new AppExecutors();
        topRatedmovie = new MutableLiveData<TopRatedMovie>();
        popularmovie = new MutableLiveData<PopularMovie>();
        creditsRepository=new CreditsRepository(application,executors);
    }


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

   public LiveData<Resource<MovieCredits>> getMovieCredits(Integer movie_id) {
        return creditsRepository.loadMovieCredits(movie_id);
    }

}