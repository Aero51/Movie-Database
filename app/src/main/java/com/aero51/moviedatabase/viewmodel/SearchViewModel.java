package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.networkonly.MovieSearchResultDataSourceFactory;

public class SearchViewModel extends ViewModel {
    private LiveData<PagedList<TopRatedMovie>> movieSearchResultPagedList;
    private LiveData<NetworkState> networkState;



    private MutableLiveData<String> searchText;


    public SearchViewModel() {
        searchText = new MutableLiveData<>();

/*
        pagedListLiveData = Transformations.switchMap(filterTextAll, input -> {
            MyDataSourceFactory myDataSourceFactory = new MyDataSourceFactory(executor,input);
            myDataSource = myDataSourceFactory.getMyDataSourceMutableLiveData();
            networkState = Transformations.switchMap(myDataSource,
                    dataSource -> dataSource.getNetworkState());
            return (new LivePagedListBuilder(myDataSourceFactory, pagedListConfig))
                    .setFetchExecutor(executor)
                    .build();

        });
*/

    }

    public LiveData<PagedList<TopRatedMovie>> getMovieSearchResult(){
        movieSearchResultPagedList=Transformations.switchMap(searchText, input -> {
            MovieSearchResultDataSourceFactory movieSearchResultDataSourceFactory=new MovieSearchResultDataSourceFactory(searchText.getValue());
           networkState=Transformations.switchMap(movieSearchResultDataSourceFactory.getNetworkStatus(),dataSource ->
               dataSource.getNetworkState());
            return(new LivePagedListBuilder(movieSearchResultDataSourceFactory, getPagedListConfig()))
                    .build();
        });
        return movieSearchResultPagedList;
    }


    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(20)
                .setInitialLoadSizeHint(30)
                .setPageSize(20).build();
    }


    public MutableLiveData<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.postValue(searchText);

    }
}
