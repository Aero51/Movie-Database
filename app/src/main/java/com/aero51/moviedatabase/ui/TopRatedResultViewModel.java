package com.aero51.moviedatabase.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.NetworkState;
import com.aero51.moviedatabase.repository.Top_Rated_Movies_Page;
import com.aero51.moviedatabase.repository.Top_Rated_Result;
import com.aero51.moviedatabase.repository.Top_Rated_Results_Repository;


public class TopRatedResultViewModel extends AndroidViewModel {
    private Top_Rated_Results_Repository repository;

    public TopRatedResultViewModel(@NonNull Application application) {
        super(application);
        repository = new Top_Rated_Results_Repository(application);
    }

    public LiveData<PagedList<Top_Rated_Result>> getNewTopRatedResultsPagedList() {
        return repository.getNewTopRatedResultsPagedList();
    }
    //used to get the page number by adding observer
    public LiveData<Top_Rated_Movies_Page> getLiveMoviePage() {
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
