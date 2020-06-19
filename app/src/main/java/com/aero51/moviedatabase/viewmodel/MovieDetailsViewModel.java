package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private Integer movie_id;

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
        movie_id =item.getId();
        popularmovie.setValue(item);
    }
    public MutableLiveData<TopRatedMovie> getTopRatedMovie() {
        return topRatedmovie;
    }
    public MutableLiveData<PopularMovie> getPopularMovie() {
        return popularmovie;
    }

   public LiveData<Resource<MovieCredits>> getMovieCredits() {
        //setting movie_id before this method gets called by adding observer which will send movie_id as null
        return creditsRepository.loadMovieCredits(movie_id);
    }

}