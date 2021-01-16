package com.aero51.moviedatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.boundarycallbacks.PopularMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.NowPlayingMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.UpcomingMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.NowPlayingMoviesDao;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.db.UpcomingMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.utils.AppExecutors;

public class MoviesRepository {

    private LiveData<PagedList<PopularMovie>> popularMoviesPagedList;
    private LiveData<PagedList<NowPlayingMovie>> nowPlayingMoviesPagedList;
    private LiveData<PagedList<UpcomingMovie>> upcomingMoviesPagedList;
    private PopularMoviesBoundaryCallback popularMoviesBoundaryCallback;
    private NowPlayingMoviesBoundaryCallback nowPlayingMoviesBoundaryCallback;
    private UpcomingMoviesBoundaryCallback upcomingMoviesBoundaryCallback;
    private AppExecutors executors;


    public MoviesRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        Database database = Database.getInstance(application);
        PopularMoviesDao popularMoviesDao = database.get_popular_movies_dao();
        NowPlayingMoviesDao nowPlayingMoviesDao = database.get_now_playing_movies_dao();
        UpcomingMoviesDao upcomingMoviesDao= database.get_upcoming_movies_dao();
        popularMoviesBoundaryCallback = new PopularMoviesBoundaryCallback(application, executors);
        nowPlayingMoviesBoundaryCallback = new NowPlayingMoviesBoundaryCallback(application, executors);
        upcomingMoviesBoundaryCallback= new UpcomingMoviesBoundaryCallback(application, executors);
        createPopularMoviesPagedList(popularMoviesDao);
        createNowPlayingMoviesPagedList(nowPlayingMoviesDao);
        createUpcomingMoviesPagedList(upcomingMoviesDao);

    }
    private PagedList.Config getPagedListConfig(){
        return  (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setInitialLoadSizeHint(60)
                .setPageSize(20).build();
    }

    private void createPopularMoviesPagedList(PopularMoviesDao dao) {
        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }
    private void createNowPlayingMoviesPagedList(NowPlayingMoviesDao dao){
        nowPlayingMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(nowPlayingMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
    }

    private void createUpcomingMoviesPagedList(UpcomingMoviesDao dao){
        upcomingMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(upcomingMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
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

    public LiveData<PagedList<NowPlayingMovie>> getNowPlayingResultsPagedList() {
        return nowPlayingMoviesPagedList;
    }

    public LiveData<NetworkState> getNowPlayingNetworkState() {
        return nowPlayingMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<NowPlayingMoviesPage> getLastNowPlayingMoviePage() {
        return nowPlayingMoviesBoundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<UpcomingMovie>> getUpcomingResultsPagedList() {
        return upcomingMoviesPagedList;
    }

    public LiveData<NetworkState> getUpcomingNetworkState() {
        return upcomingMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<UpcomingMoviesPage> getLastUpcomingMoviePage() {
        return upcomingMoviesBoundaryCallback.getCurrent_movie_page();
    }
}
