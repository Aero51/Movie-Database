package com.aero51.moviedatabase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowSearchResult;
import com.aero51.moviedatabase.repository.networkonly.MovieSearchDataSourceFactory;
import com.aero51.moviedatabase.repository.networkonly.PeopleSearchDataSourceFactory;
import com.aero51.moviedatabase.repository.networkonly.TvShowSearchDataSourceFactory;

public class SearchViewModel extends ViewModel {
    private LiveData<PagedList<NowPlayingMoviesPage.NowPlayingMovie>> movieSearchPagedList;
    private LiveData<PagedList<TvShowSearchResult.TvShow>> tvShowSearchPagedList;
    private LiveData<PagedList<ActorSearchResponse.ActorSearch>> peopleSearchPagedList;
    private LiveData<NetworkState> networkState;


    private MutableLiveData<String> movieSearchText;
    private MutableLiveData<String> tvShowSearchText;
    private MutableLiveData<String> peopleSearchText;


    public SearchViewModel() {
        movieSearchText = new MutableLiveData<>();
        tvShowSearchText = new MutableLiveData<>();
        peopleSearchText= new MutableLiveData<>();
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

        peopleSearchPagedList = Transformations.switchMap(peopleSearchText, input -> {
            PeopleSearchDataSourceFactory peopleSearchDataSourceFactory = new PeopleSearchDataSourceFactory(peopleSearchText.getValue());
            networkState = Transformations.switchMap(peopleSearchDataSourceFactory.getNetworkStatus(), dataSource ->
                    dataSource.getNetworkState());
            return (new LivePagedListBuilder(peopleSearchDataSourceFactory, getPagedListConfig()))
                    .build();
        });
    }


    public LiveData<PagedList<NowPlayingMoviesPage.NowPlayingMovie>> getMovieSearchResult() {
        return movieSearchPagedList;
    }

    public LiveData<PagedList<TvShowSearchResult.TvShow>> getTvShowSearchResult() {
        return tvShowSearchPagedList;
    }

    public LiveData<PagedList<ActorSearchResponse.ActorSearch>> getPeopleSearchResult() {
        return peopleSearchPagedList;
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

    public MutableLiveData<String> getTvShowSearchText() {
        return tvShowSearchText;
    }

    public MutableLiveData<String> getPeopleSearchText() {
        return peopleSearchText;
    }

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

    public void setPeopleSearchText(String peopleSearchText) {
        if (!peopleSearchText.isEmpty()) {
            this.peopleSearchText.postValue(peopleSearchText);
        }
    }
}
