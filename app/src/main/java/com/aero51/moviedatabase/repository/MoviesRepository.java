package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.boundarycallbacks.PopularMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.TopRatedMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.AppExecutors;

public class MoviesRepository {
    private LiveData<PagedList<TopRatedMovie>> topRatedMoviesPagedList;
    private LiveData<PagedList<PopularMovie>> popularMoviesPagedList;
    private TopRatedMoviesBoundaryCallback topRatedMoviesBoundaryCallback;
    private PopularMoviesBoundaryCallback popularMoviesBoundaryCallback;
    private AppExecutors executors;


    public MoviesRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        TopRatedMoviesDao topRatedMoviesDao = database.get_top_rated_movies_dao();
        PopularMoviesDao popularMoviesDao = database.get_popular_movies_dao();
        topRatedMoviesBoundaryCallback = new TopRatedMoviesBoundaryCallback(application, executors);
        popularMoviesBoundaryCallback = new PopularMoviesBoundaryCallback(application, executors);
        createTopRatedMoviesPagedList(topRatedMoviesDao);
        createPopularMoviesPagedList(popularMoviesDao);

    }
    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build();
    }

    private void createTopRatedMoviesPagedList(TopRatedMoviesDao dao){
        topRatedMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(topRatedMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();

    }
    private void createPopularMoviesPagedList(PopularMoviesDao dao) {
        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }



    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return topRatedMoviesPagedList;
    }

    public LiveData<NetworkState> getTopRatedNetworkState() {
        return topRatedMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<TopRatedMoviesPage> getLastTopRatedMoviePage() {
        return topRatedMoviesBoundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesPagedList;
    }

    public LiveData<NetworkState> getPopularNetworkState() {
        return popularMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<PopularMoviesPage> getLastPopularMoviePage() {
        return popularMoviesBoundaryCallback.getCurrent_movie_page();
    }

}
