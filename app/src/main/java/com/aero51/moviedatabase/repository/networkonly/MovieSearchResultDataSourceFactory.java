package com.aero51.moviedatabase.repository.networkonly;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MovieSearchResultDataSourceFactory extends DataSource.Factory {



    private MutableLiveData< MovieSearchResultDataSource> networkStatus;
    private String search;

    public MovieSearchResultDataSourceFactory(String search) {
        this.networkStatus = new MutableLiveData<>();
        this.search=search;
    }


    @NonNull
    @Override
    public DataSource create() {
        MovieSearchResultDataSource movieSearchResultDataSource = new MovieSearchResultDataSource(search);
        networkStatus.postValue(movieSearchResultDataSource);

        return movieSearchResultDataSource;
    }

    public MutableLiveData<MovieSearchResultDataSource> getNetworkStatus() {
        return networkStatus;
    }

}
