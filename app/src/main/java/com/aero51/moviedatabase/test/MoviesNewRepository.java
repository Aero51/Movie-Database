package com.aero51.moviedatabase.test;

import android.app.Application;

import androidx.lifecycle.LiveData;

import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;


import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.AppExecutors;

import static com.aero51.moviedatabase.utils.Constants.POPULAR_MOVIE_TYPE_ID;
import static com.aero51.moviedatabase.utils.Constants.TOP_RATED_MOVIE_TYPE_ID;

public class MoviesNewRepository {

    private LiveData<PagedList<Movie>> topRatedMoviesPagedList;
    private LiveData<PagedList<Movie>> popularMoviesPagedList;
    private MoviesBoundaryCallback topRatedMoviesBoundaryCallback;
    private MoviesBoundaryCallback popularMoviesBoundaryCallback;
    private AppExecutors executors;

    public MoviesNewRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        MoviesDao moviesDao=database.get_movies_dao();
        topRatedMoviesBoundaryCallback=new MoviesBoundaryCallback(application,executors,TOP_RATED_MOVIE_TYPE_ID);
        popularMoviesBoundaryCallback= new MoviesBoundaryCallback(application,executors,POPULAR_MOVIE_TYPE_ID);
        createTopRatedMoviesPagedList(moviesDao);
    }

    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build();
    }
    private void createTopRatedMoviesPagedList(MoviesDao dao){
        topRatedMoviesPagedList = new LivePagedListBuilder<>(dao.getMovieResults(TOP_RATED_MOVIE_TYPE_ID),getPagedListConfig())
                .setBoundaryCallback(topRatedMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();

    }


    private void createPopularMoviesPagedList(MoviesDao dao) {
        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getMovieResults(POPULAR_MOVIE_TYPE_ID), getPagedListConfig())
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }

    public LiveData<PagedList<Movie>> getTopRatedMoviesPagedList() {
        return topRatedMoviesPagedList;
    }

    public LiveData<PagedList<Movie>> getPopularMoviesPagedList() {
        return popularMoviesPagedList;
    }

}
