package com.aero51.moviedatabase.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class TopRatedResultDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Top_Rated_Result>> topRatedResultLiveDataSource;

    @NonNull
    @Override
    public DataSource create() {
        topRatedResultLiveDataSource = new MutableLiveData<>();
        TopRatedResultDataSource topRatedResultDataSource = new TopRatedResultDataSource();
        topRatedResultLiveDataSource.postValue(topRatedResultDataSource);
        return topRatedResultDataSource;
    }

    //getter for topRatedResultLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Top_Rated_Result>> getTopRatedResultLiveDataSource() {
        return topRatedResultLiveDataSource;
    }
}
