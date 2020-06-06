package com.aero51.moviedatabase.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class TopRatedResultDataSourceFactory extends DataSource.Factory<Integer, Top_Rated_Result> {


    @NonNull
    @Override
    public DataSource<Integer, Top_Rated_Result> create() {
        MutableLiveData<PageKeyedDataSource> topRatedResultLiveDataSource = new MutableLiveData<>();
        TopRatedResultDataSource topRatedResultDataSource = new TopRatedResultDataSource();
        topRatedResultLiveDataSource.postValue(topRatedResultDataSource);
        return topRatedResultDataSource;
    }




}
