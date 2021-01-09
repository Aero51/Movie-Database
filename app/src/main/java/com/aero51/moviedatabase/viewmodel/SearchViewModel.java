package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShow;
import com.aero51.moviedatabase.repository.networkonly.MovieSearchDataSourceFactory;
import com.aero51.moviedatabase.repository.networkonly.TvShowSearchDataSourceFactory;

public class SearchViewModel extends ViewModel {
    private LiveData<PagedList<TopRatedMovie>> movieSearchPagedList;
    private LiveData<PagedList<TvShow>> tvShowSearchPagedList;
    private LiveData<NetworkState> networkState;


    private MutableLiveData<String> movieSearchText;
    private MutableLiveData<String> tvShowSearchText;


    public SearchViewModel() {
        movieSearchText = new MutableLiveData<>();
        tvShowSearchText = new MutableLiveData<>();
        initPagedLists();

    }


    private void initPagedLists() {
        movieSearchPagedList = Transformations.switchMap(movieSearchText, input -> {
            MovieSearchDataSourceFactory movieSearchDataSourceFactory = new MovieSearchDataSourceFactory(movieSearchText.getValue());
            networkState = Transformations.switchMap(movieSearchDataSourceFactory.getNetworkStatus(), dataSource ->
                    dataSource.getNetworkState());
            return (new LivePagedListBuilder(movieSearchDataSourceFactory, getPagedListConfig()))
                    .build();
        });

        tvShowSearchPagedList = Transformations.switchMap(tvShowSearchText, input -> {
            TvShowSearchDataSourceFactory tvShowSearchDataSourceFactory = new TvShowSearchDataSourceFactory(tvShowSearchText.getValue());
            networkState = Transformations.switchMap(tvShowSearchDataSourceFactory.getNetworkStatus(), dataSource ->
                    dataSource.getNetworkState());
            return (new LivePagedListBuilder(tvShowSearchDataSourceFactory, getPagedListConfig()))
                    .build();
        });
    }


    public LiveData<PagedList<TopRatedMovie>> getMovieSearchResult() {
        return movieSearchPagedList;
    }

    public LiveData<PagedList<TvShow>> getTvShowSearchResult() {
        return tvShowSearchPagedList;
    }


    private PagedList.Config getPagedListConfig() {
        return (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(20)
                .setInitialLoadSizeHint(20)
                .setPageSize(20).build();
    }


    public MutableLiveData<String> getMovieSearchText() {
        return movieSearchText;
    }

    public MutableLiveData<String> getTvShowSearchText() { return tvShowSearchText; }

    public void setMovieSearchText(String movieSearchText) {
        if (!movieSearchText.isEmpty()) {
            this.movieSearchText.postValue(movieSearchText);
        }
    }
    public void setTvShowSearchText(String tvShowSearchText) {
        if (!tvShowSearchText.isEmpty()) {
            this.tvShowSearchText.postValue(tvShowSearchText);
        }
    }
}
