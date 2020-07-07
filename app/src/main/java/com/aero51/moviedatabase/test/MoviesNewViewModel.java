package com.aero51.moviedatabase.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.utils.AppExecutors;

public class MoviesNewViewModel  extends AndroidViewModel {
    private MoviesNewRepository moviesNewRepository;

    public MoviesNewViewModel(@NonNull Application application) {
        super(application);
        AppExecutors  executors = new AppExecutors();
        moviesNewRepository = new MoviesNewRepository(application,executors);
    }

    public LiveData<PagedList<Movie>> getTopRatedMoviesPagedList() {
        return moviesNewRepository.getTopRatedMoviesPagedList();
    }
    public LiveData<PagedList<Movie>> getPopularMoviesPagedList() {
        return moviesNewRepository.getPopularMoviesPagedList();
    }


}
