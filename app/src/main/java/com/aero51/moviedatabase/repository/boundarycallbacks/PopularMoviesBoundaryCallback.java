package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;
import static com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE;
import static com.aero51.moviedatabase.utils.Constants.REGION;

public class PopularMoviesBoundaryCallback extends PagedList.BoundaryCallback<PopularMovie> {
    private AppExecutors executors;
    private Database database;
    private PopularMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<PopularMoviesPage> current_movie_page;


    public PopularMoviesBoundaryCallback(Application application, AppExecutors executors) {
        // super();
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_popular_movies_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.d("moviedatabaselog", "popularMovies onzeroitemsloaded");
        fetchPopularMovies(MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull PopularMovie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d("moviedatabaselog", "popularMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull PopularMovie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_movie_page.getValue().getPage() + 1;
        Log.d("moviedatabaselog", "popularMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularMovies(page_number);
    }

    public void fetchPopularMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<PopularMoviesPage> call = theMovieDbApi.getPopularMovies(API_KEY, pageNumber, REGION);
        call.enqueue(new Callback<PopularMoviesPage>() {
            @Override
            public void onResponse(Call<PopularMoviesPage> call, Response<PopularMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "popularMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "popularMovies Response ok: " + response.code());
                PopularMoviesPage mPopularMovies = response.body();
                insertListToDb(mPopularMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<PopularMoviesPage> call, Throwable t) {
                Log.d("moviedatabaselog", "popularMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    public void insertListToDb(PopularMoviesPage page) {
        List<PopularMovie> listOfResults = page.getResults_list();

        Runnable runnable = () -> {
            dao.deleteAllMoviePages();
            dao.insertMoviePage(page);
            dao.insertList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PopularMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }
}
