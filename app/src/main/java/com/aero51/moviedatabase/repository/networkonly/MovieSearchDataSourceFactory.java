package com.aero51.moviedatabase.repository.networkonly;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MovieSearchDataSourceFactory extends DataSource.Factory {



    private MutableLiveData<MovieSearchDataSource> networkStatus;
    private String search;

    public MovieSearchDataSourceFactory(String search) {
        this.networkStatus = new MutableLiveData<>();
        this.search=search;
    }


    @NonNull
    @Override
    public DataSource create() {
        MovieSearchDataSource movieSearchDataSource = new MovieSearchDataSource(search);
        networkStatus.postValue(movieSearchDataSource);

        return movieSearchDataSource;
    }

    public MutableLiveData<MovieSearchDataSource> getNetworkStatus() {
        return networkStatus;
    }

}
