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
    private PopularMoviesBoundaryCallback boundaryCallback;

    public PopularMoviesRepository(Application application, AppExecutors executors) {

        boundaryCallback = new PopularMoviesBoundaryCallback(application, executors);
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        PopularMoviesDao dao = database.get_popular_movies_dao();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(40)
                        .setInitialLoadSizeHint(60)
                        .setPageSize(20).build();

        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(boundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }


    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return boundaryCallback.getNetworkState();
    }

    public LiveData<PopularMoviesPage> getCurrent_movie_page() {
        return boundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesPagedList;
    }
}
