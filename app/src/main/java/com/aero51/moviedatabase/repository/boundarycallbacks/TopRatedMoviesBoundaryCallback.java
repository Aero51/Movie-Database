package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE;
import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;
import static com.aero51.moviedatabase.utils.Constants.REGION;


public class TopRatedMoviesBoundaryCallback extends PagedList.BoundaryCallback<TopRatedMoviesPage.TopRatedMovie> {
    private AppExecutors executors;
    private Database database;
    private TopRatedMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<TopRatedMoviesPage> current_movie_page;

    public TopRatedMoviesBoundaryCallback(Application application, AppExecutors executors) {
        //super();
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_top_rated_movies_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        fetchTopRatedMovies(MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull TopRatedMoviesPage.TopRatedMovie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull TopRatedMoviesPage.TopRatedMovie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_movie_page.getValue().getPage() + 1;
        fetchTopRatedMovies(page_number);
    }

    public void fetchTopRatedMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<TopRatedMoviesPage> call = theMovieDbApi.getTopRatedMovies(TMDB_API_KEY, pageNumber, REGION,4000,"vote_average.desc",16);
        call.enqueue(new Callback<TopRatedMoviesPage>() {
            @Override
            public void onResponse(Call<TopRatedMoviesPage> call, Response<TopRatedMoviesPage> response) {
                if (!response.isSuccessful()) {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                TopRatedMoviesPage mTopRatedMovies = response.body();
                insertListToDb(mTopRatedMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<TopRatedMoviesPage> call, Throwable t) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }


    public void insertListToDb(TopRatedMoviesPage page) {
        List<TopRatedMoviesPage.TopRatedMovie> listOfResults = page.getResults_list();
        Runnable runnable = () -> {
            dao.deleteAllMoviePages();
            dao.insertMoviePage(page);
            Long currentTime=System.currentTimeMillis();
            for (TopRatedMoviesPage.TopRatedMovie movie : listOfResults)
            {
                movie.setTimestamp(currentTime);
            }
            dao.insertList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<TopRatedMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }


}
