package com.aero51.moviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.TopRatedMoviesRepository;


public class TopRatedResultViewModel extends AndroidViewModel {
    private TopRatedMoviesRepository repository;

    public TopRatedResultViewModel(@NonNull Application application) {
        super(application);
        repository = new TopRatedMoviesRepository(application);
    }

    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return repository.getTopRatedResultsPagedList();
    }
    //used to get the page number by adding observer
    public LiveData<TopRatedMoviesPage> getLiveMoviePage() {
        return repository.getCurrent_movie_page();
    }


    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }



    @Override
    protected void onCleared() {
        Log.d("moviedatabaselog", "view model on cleared ");
       // repository.getMoviePageLd().removeObserver(repository.getObserver());
        super.onCleared();
    }

}
