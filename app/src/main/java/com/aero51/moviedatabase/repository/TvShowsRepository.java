package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.aero51.moviedatabase.repository.boundarycallbacks.PopularTvShowsBoundaryCallback;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.PopularTvShowsDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.utils.AppExecutors;

public class TvShowsRepository {


    private LiveData<PagedList<PopularTvShowsPage.PopularTvShow>> popularTvShowsPagedList;
    private PopularTvShowsBoundaryCallback popularTvShowsBoundaryCallback;
    private AppExecutors executors;

    public TvShowsRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        Database database = Database.getInstance(application);
        PopularTvShowsDao popularTvShowsDao = database.get_popular_tv_shows_dao();
        popularTvShowsBoundaryCallback= new PopularTvShowsBoundaryCallback(application, executors);
        createPopularTvShowsPagedList(popularTvShowsDao);
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
    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build();
    }

}
