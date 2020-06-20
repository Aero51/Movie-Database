package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.movie.PopularMoviesPage;

import com.aero51.moviedatabase.utils.AppExecutors;

public class PopularMoviesRepository {

    private LiveData<PagedList<PopularMovie>> popularMoviesPagedList;
    private PopularMoviesBoundaryCallback popularMoviesBoundaryCallback;
    private AppExecutors executors;

    public PopularMoviesRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        popularMoviesBoundaryCallback = new PopularMoviesBoundaryCallback(application, executors);
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        PopularMoviesDao dao = database.get_popular_movies_dao();
        createPopularMoviesPagedList(dao);

    }

    private void createPopularMoviesPagedList(PopularMoviesDao dao) {
        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(40)
                        .setInitialLoadSizeHint(60)
                        .setPageSize(20).build();

        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();

    }

    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return popularMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<PopularMoviesPage> getCurrent_movie_page() {
        return popularMoviesBoundaryCallback.getCurrent_movie_page();
    }

}
