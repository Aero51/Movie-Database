package com.aero51.moviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.PopularMoviesRepository;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.TopRatedMoviesRepository;


public class MoviesViewModel extends AndroidViewModel {
    private TopRatedMoviesRepository topRatedMoviesRepository;
    private PopularMoviesRepository popularMoviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        topRatedMoviesRepository = new TopRatedMoviesRepository(application);
        popularMoviesRepository= new PopularMoviesRepository(application);
    }

    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return topRatedMoviesRepository.getTopRatedResultsPagedList();
    }
    //used to get the page number by adding observer
    public LiveData<TopRatedMoviesPage> getTopRatedLiveMoviePage() {
        return topRatedMoviesRepository.getCurrent_movie_page();
    }

    public LiveData<NetworkState> getTopRatedMoviesNetworkState() {
        return topRatedMoviesRepository.getNetworkState();
    }



    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesRepository.getPopularResultsPagedList();
    }
    //used to get the page number by adding observer
    public LiveData<PopularMoviesPage> getPopularLiveMoviePage() {
        return popularMoviesRepository.getCurrent_movie_page();
    }
    public LiveData<NetworkState> getPopularMoviesNetworkState() {
        return popularMoviesRepository.getNetworkState();
    }


    @Override
    protected void onCleared() {
        Log.d("moviedatabaselog", "view model on cleared ");
       // repository.getMoviePageLd().removeObserver(repository.getObserver());
        super.onCleared();
    }

}
