package com.aero51.moviedatabase.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.NetworkState;
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

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

    public void insert(Top_Rated_Result result) {
        repository.insert(result);
    }

    public void update(Top_Rated_Result result) {
        repository.update(result);
    }

    public void delete(Top_Rated_Result result) {
        repository.delete(result);
    }

    public void deleteAllResults() {
        repository.deleteAllResults();
    }


}
