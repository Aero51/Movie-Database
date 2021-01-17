package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.TvShowsRepository;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.utils.AppExecutors;

public class TvShowsViewModel extends AndroidViewModel {
    private TvShowsRepository tvShowsRepository;

    public TvShowsViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        tvShowsRepository=new TvShowsRepository(application, executors);
    }
    public LiveData<PagedList<PopularTvShowsPage.PopularTvShow>> getTvPopularResultsPagedList() {
        return tvShowsRepository.getTvPopularResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<PopularTvShowsPage> getPopularLiveTvShowPage() {
        return tvShowsRepository.getLastPopularTvShowPage();
    }

    public LiveData<NetworkState> getPopularMoviesNetworkState() {
        return tvShowsRepository.getPopularNetworkState();
    }
}
