package com.aero51.moviedatabase.repository.networkonly;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

//public class TopRatedResultDataSourceFactory extends DataSource.Factory<Integer, Top_Rated_Result> {
public class TopRatedResultDataSourceFactory extends DataSource.Factory{

   // private  MutableLiveData<PageKeyedDataSource> topRatedResultLiveDataSource;
    private MutableLiveData<TopRatedResultDataSource> networkStatus;

    public TopRatedResultDataSourceFactory() {
       // topRatedResultLiveDataSource = new MutableLiveData<>();
        this.networkStatus = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        TopRatedResultDataSource topRatedResultDataSource = new TopRatedResultDataSource();
        networkStatus.postValue(topRatedResultDataSource);
      //  topRatedResultLiveDataSource.postValue(topRatedResultDataSource);

        return topRatedResultDataSource;
    }

    public MutableLiveData<TopRatedResultDataSource> getNetworkStatus() {
        return networkStatus;
    }


}
