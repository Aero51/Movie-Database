package com.aero51.moviedatabase.repository.networkonly;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class TvShowSearchDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<TvShowSearchDataSource> networkStatus;
    private String search;

    public TvShowSearchDataSourceFactory(String search) {
        this.networkStatus = new MutableLiveData<>();
        this.search = search;
    }


    @NonNull
    @Override
    public DataSource create() {
        TvShowSearchDataSource tvShowSearchDataSource = new TvShowSearchDataSource(search);
        networkStatus.postValue(tvShowSearchDataSource);

        return tvShowSearchDataSource;
    }

    public MutableLiveData<TvShowSearchDataSource> getNetworkStatus() {
        return networkStatus;
    }
}
