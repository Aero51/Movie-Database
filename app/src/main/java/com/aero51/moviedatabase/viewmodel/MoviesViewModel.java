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
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        AppExecutors  executors = new AppExecutors();
        moviesRepository = new MoviesRepository(application, executors);
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


    public LiveData<PagedList<NowPlayingMovie>> getNowPlayingResultsPagedList() {
        return moviesRepository.getNowPlayingResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<NowPlayingMoviesPage> getNowPlayingLiveMoviePage() {
        return moviesRepository.getLastNowPlayingMoviePage();
    }

    public LiveData<NetworkState> getNowPlayingMoviesNetworkState() {
        return moviesRepository.getNowPlayingNetworkState();
    }
    public LiveData<PagedList<UpcomingMovie>> getUpcomingResultsPagedList() {
        return moviesRepository.getUpcomingResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<UpcomingMoviesPage> getUpcomingLiveMoviePage() {
        return moviesRepository.getLastUpcomingMoviePage();
    }

    public LiveData<NetworkState> getUpcomingMoviesNetworkState() {
        return moviesRepository.getUpcomingNetworkState();
    }

    @Override
    protected void onCleared() {
        Log.d(Constants.LOG, "view model on cleared ");
        // repository.getMoviePageLd().removeObserver(repository.getObserver());
        super.onCleared();
    }

}
