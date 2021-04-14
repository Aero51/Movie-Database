package com.aero51.moviedatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.TvShowsRepository;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

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

    public LiveData<NetworkState> getPopularTvShowsNetworkState() {
        return tvShowsRepository.getPopularNetworkState();
    }


    public LiveData<PagedList<AiringTvShowsPage.AiringTvShow>> getTvAiringResultsPagedList() {
        return tvShowsRepository.getTvAiringResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<AiringTvShowsPage> getAiringLiveTvShowPage() {
        return tvShowsRepository.getLastAiringTvShowPage();
    }

    public LiveData<NetworkState> getAiringTvShowsNetworkState() {
        return tvShowsRepository.getAiringNetworkState();
    }

    public LiveData<PagedList<TrendingTvShowsPage.TrendingTvShow>> getTvTrendingResultsPagedList() {
        return tvShowsRepository.getTvTrendingResultsPagedList();
    }

    //used to get the page number by adding observer
    public LiveData<TrendingTvShowsPage> getTrendingLiveTvShowPage() {
        return tvShowsRepository.getLastTrendingTvShowPage();
    }

    public LiveData<NetworkState> getTrendingTvShowsNetworkState() {
        return tvShowsRepository.getTrendingNetworkState();
    }
    public LiveData<Resource<List<TvShowGenresResponse.TvShowGenre>>> getTvShowsGenres() {
        return  tvShowsRepository.loadTvShowsGenres();
    }

}
