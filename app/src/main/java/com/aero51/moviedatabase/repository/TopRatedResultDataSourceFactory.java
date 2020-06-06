package com.aero51.moviedatabase.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

//public class TopRatedResultDataSourceFactory extends DataSource.Factory<Integer, Top_Rated_Result> {
public class TopRatedResultDataSourceFactory extends DataSource.Factory{

    private  MutableLiveData<PageKeyedDataSource> topRatedResultLiveDataSource;

    public TopRatedResultDataSourceFactory() {
        topRatedResultLiveDataSource = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        Log.d("moviedatabaselog", "TopRatedResultDataSourceFactory  create() ");
        TopRatedResultDataSource topRatedResultDataSource = new TopRatedResultDataSource();
        topRatedResultLiveDataSource.postValue(topRatedResultDataSource);
        return topRatedResultDataSource;
    }




}
