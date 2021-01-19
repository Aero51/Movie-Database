package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.boundarycallbacks.AiringTvShowsBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.PopularTvShowsBoundaryCallback;
import com.aero51.moviedatabase.repository.db.AiringTvShowsDao;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.PopularTvShowsDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.utils.AppExecutors;

public class TvShowsRepository {


    private LiveData<PagedList<PopularTvShowsPage.PopularTvShow>> popularTvShowsPagedList;
    private LiveData<PagedList<AiringTvShowsPage.AiringTvShow>> airingTvShowsPagedList;
    private PopularTvShowsBoundaryCallback popularTvShowsBoundaryCallback;
    private AiringTvShowsBoundaryCallback airingTvShowsBoundaryCallback;
    private AppExecutors executors;

    public TvShowsRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        Database database = Database.getInstance(application);
        PopularTvShowsDao popularTvShowsDao = database.get_popular_tv_shows_dao();
        AiringTvShowsDao airingTvShowsDao=database.get_airing_tv_shows_dao();
        popularTvShowsBoundaryCallback= new PopularTvShowsBoundaryCallback(application, executors);
        airingTvShowsBoundaryCallback= new AiringTvShowsBoundaryCallback(application, executors);
        createPopularTvShowsPagedList(popularTvShowsDao);
        createAiringTvShowsPagedList(airingTvShowsDao);
    }

    private void createPopularTvShowsPagedList(PopularTvShowsDao dao) {
        popularTvShowsPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(popularTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }
    public LiveData<PagedList<PopularTvShowsPage.PopularTvShow>> getTvPopularResultsPagedList() {
        return popularTvShowsPagedList;
    }
    public LiveData<PopularTvShowsPage> getLastPopularTvShowPage() {
        return popularTvShowsBoundaryCallback.getCurrent_Tv_shows_page();
    }

    public LiveData<NetworkState> getPopularNetworkState() {
        return popularTvShowsBoundaryCallback.getNetworkState();
    }


    private void createAiringTvShowsPagedList(AiringTvShowsDao dao) {
        airingTvShowsPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(airingTvShowsBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }
    public LiveData<PagedList<AiringTvShowsPage.AiringTvShow>> getTvAiringResultsPagedList() {
        return airingTvShowsPagedList;
    }
    public LiveData<AiringTvShowsPage> getLastAiringTvShowPage() {
        return airingTvShowsBoundaryCallback.getCurrent_Tv_shows_page();
    }

    public LiveData<NetworkState> getAiringNetworkState() {
        return airingTvShowsBoundaryCallback.getNetworkState();
    }



    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build();
    }

}
