package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.AppExecutors;


public class TopRatedMoviesRepository {

    private LiveData<PagedList<TopRatedMovie>> topRatedMoviesPagedList;
    private TopRatedMoviesBoundaryCallback boundaryCallback;
    private AppExecutors executors;

    public TopRatedMoviesRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        TopRatedMoviesDao dao = database.get_top_rated_movies_dao();
        boundaryCallback = new TopRatedMoviesBoundaryCallback(application, executors);
        createTopRatedMoviesPagedList(dao);


    }
    private void createTopRatedMoviesPagedList(TopRatedMoviesDao dao){
        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(40)
                        .setInitialLoadSizeHint(60)
                        .setPageSize(20).build();

        //  Executor executor = Executors.newFixedThreadPool(5);
        topRatedMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(boundaryCallback).setFetchExecutor(executors.networkIO())
                .build();

    }

    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return topRatedMoviesPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return boundaryCallback.getNetworkState();
    }

    public LiveData<TopRatedMoviesPage> getCurrent_movie_page() {
        return boundaryCallback.getCurrent_movie_page();
    }
}