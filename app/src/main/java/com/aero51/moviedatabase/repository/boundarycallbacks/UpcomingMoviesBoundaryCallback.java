package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.UpcomingMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.REGION;
import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;
import static com.aero51.moviedatabase.utils.Constants.UPCOMING_MOVIES_FIRST_PAGE;

public class UpcomingMoviesBoundaryCallback extends PagedList.BoundaryCallback<UpcomingMovie> {
    private AppExecutors executors;
    private Database database;
    private UpcomingMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<UpcomingMoviesPage> current_movie_page;

    public UpcomingMoviesBoundaryCallback(Application application, AppExecutors executors) {
        //super();
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_upcoming_movies_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        //Log.d(Constants.LOG, "upcomingMovies onzeroitemsloaded");
        fetchUpcomingMovies(UPCOMING_MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull UpcomingMovie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d(Constants.LOG, "upcomingMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull UpcomingMovie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_movie_page.getValue().getPage() + 1;
        //Log.d(Constants.LOG, "upcomingMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchUpcomingMovies(page_number);
    }

    public void fetchUpcomingMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<UpcomingMoviesPage> call = theMovieDbApi.getUpcomingMovies(TMDB_API_KEY, pageNumber, "");
        call.enqueue(new Callback<UpcomingMoviesPage>() {
            @Override
            public void onResponse(Call<UpcomingMoviesPage> call, Response<UpcomingMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d(Constants.LOG, "upcomingMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d(Constants.LOG, "upcomingMovies Response ok: " + response.code());
                UpcomingMoviesPage upcomingMoviesPage = response.body();
                insertListToDb(upcomingMoviesPage);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<UpcomingMoviesPage> call, Throwable t) {
                Log.d(Constants.LOG, "upcomingMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }


    public void insertListToDb(UpcomingMoviesPage page) {
        List<UpcomingMovie> listOfResults = page.getResults_list();
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

    public LiveData<UpcomingMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }


}
