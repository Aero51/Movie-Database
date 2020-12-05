package com.aero51.moviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.MoviesRepository;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        AppExecutors  executors = new AppExecutors();
        moviesRepository = new MoviesRepository(application, executors);
    }


    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return moviesRepository.getTopRatedResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<TopRatedMoviesPage> getTopRatedLiveMoviePage() {
        return moviesRepository.getLastTopRatedMoviePage();
    }

    public LiveData<NetworkState> getTopRatedMoviesNetworkState() {
        return moviesRepository.getTopRatedNetworkState();
    }


    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return moviesRepository.getPopularResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<PopularMoviesPage> getPopularLiveMoviePage() {
        return moviesRepository.getLastPopularMoviePage();
    }

    public LiveData<NetworkState> getPopularMoviesNetworkState() {
        return moviesRepository.getPopularNetworkState();
    }


    @Override
    protected void onCleared() {
        Log.d(Constants.LOG, "view model on cleared ");
        // repository.getMoviePageLd().removeObserver(repository.getObserver());
        super.onCleared();
    }

}
