package com.aero51.moviedatabase.repository.networkonly;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PeopleSearchDataSourceFactory extends DataSource.Factory {


    private MutableLiveData<PeopleSearchDataSource> networkStatus;
    private String search;



    public PeopleSearchDataSourceFactory(String search) {
        this.networkStatus = new MutableLiveData<>();
        this.search=search;
    }


    @NonNull
    @Override
    public DataSource create() {
        PeopleSearchDataSource peopleSearchDataSource = new PeopleSearchDataSource(search);
        networkStatus.postValue(peopleSearchDataSource);
        return peopleSearchDataSource;
    }

    public MutableLiveData<PeopleSearchDataSource> getNetworkStatus() {
        return networkStatus;
    }
}
