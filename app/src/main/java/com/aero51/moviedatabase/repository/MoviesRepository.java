package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.boundarycallbacks.PopularMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.NowPlayingMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.TopRatedMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.boundarycallbacks.UpcomingMoviesBoundaryCallback;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.NowPlayingMoviesDao;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.db.UpcomingMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.repository.networkboundresources.CastNetworkBoundResource;
import com.aero51.moviedatabase.repository.networkboundresources.MovieGenresNetworkBoundResource;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class MoviesRepository {

    private LiveData<PagedList<TopRatedMoviesPage.TopRatedMovie>> topRatedMoviesPagedList;
    private LiveData<PagedList<PopularMoviesPage.PopularMovie>> popularMoviesPagedList;
    private LiveData<PagedList<NowPlayingMoviesPage.NowPlayingMovie>> nowPlayingMoviesPagedList;
    private LiveData<PagedList<UpcomingMoviesPage.UpcomingMovie>> upcomingMoviesPagedList;
    private TopRatedMoviesBoundaryCallback topRatedMoviesBoundaryCallback;
    private PopularMoviesBoundaryCallback popularMoviesBoundaryCallback;
    private NowPlayingMoviesBoundaryCallback nowPlayingMoviesBoundaryCallback;
    private UpcomingMoviesBoundaryCallback upcomingMoviesBoundaryCallback;
    private AppExecutors executors;
    private Application application;


    public MoviesRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        this.application=application;
        Database database = Database.getInstance(application);
        TopRatedMoviesDao topRatedMoviesDao = database.get_top_rated_movies_dao();
        PopularMoviesDao popularMoviesDao = database.get_popular_movies_dao();
        NowPlayingMoviesDao nowPlayingMoviesDao = database.get_now_playing_movies_dao();
        UpcomingMoviesDao upcomingMoviesDao= database.get_upcoming_movies_dao();
        topRatedMoviesBoundaryCallback= new TopRatedMoviesBoundaryCallback(application, executors);
        popularMoviesBoundaryCallback = new PopularMoviesBoundaryCallback(application, executors);
        nowPlayingMoviesBoundaryCallback = new NowPlayingMoviesBoundaryCallback(application, executors);
        upcomingMoviesBoundaryCallback= new UpcomingMoviesBoundaryCallback(application, executors);
        createTopRatedMoviesPagedList(topRatedMoviesDao);
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

    private void createTopRatedMoviesPagedList(TopRatedMoviesDao dao) {
        topRatedMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), getPagedListConfig())
                .setBoundaryCallback(topRatedMoviesBoundaryCallback).setFetchExecutor(executors.networkIO())
                .build();
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

    public LiveData<PagedList<TopRatedMoviesPage.TopRatedMovie>> getTopRatedResultsPagedList() {
        return topRatedMoviesPagedList;
    }

    public LiveData<NetworkState> getTopRatedNetworkState() {
        return topRatedMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<TopRatedMoviesPage> getLastTopRatedMoviePage() {
        return topRatedMoviesBoundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<PopularMoviesPage.PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesPagedList;
    }

    public LiveData<NetworkState> getPopularNetworkState() {
        return popularMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<PopularMoviesPage> getLastPopularMoviePage() {
        return popularMoviesBoundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<NowPlayingMoviesPage.NowPlayingMovie>> getNowPlayingResultsPagedList() {
        return nowPlayingMoviesPagedList;
    }

    public LiveData<NetworkState> getNowPlayingNetworkState() {
        return nowPlayingMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<NowPlayingMoviesPage> getLastNowPlayingMoviePage() {
        return nowPlayingMoviesBoundaryCallback.getCurrent_movie_page();
    }

    public LiveData<PagedList<UpcomingMoviesPage.UpcomingMovie>> getUpcomingResultsPagedList() {
        return upcomingMoviesPagedList;
    }

    public LiveData<NetworkState> getUpcomingNetworkState() {
        return upcomingMoviesBoundaryCallback.getNetworkState();
    }

    public LiveData<UpcomingMoviesPage> getLastUpcomingMoviePage() {
        return upcomingMoviesBoundaryCallback.getCurrent_movie_page();
    }


    public LiveData<Resource<List<MovieGenresResponse.MovieGenre>>> loadMoviesGenres() {
        return new MovieGenresNetworkBoundResource(executors,application).asLiveData();
    }



}
