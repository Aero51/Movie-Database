package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.NowPlayingMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;
import static com.aero51.moviedatabase.utils.Constants.REGION;
import static com.aero51.moviedatabase.utils.Constants.NOW_PLAYING_MOVIES_FIRST_PAGE;

public class NowPlayingMoviesBoundaryCallback extends PagedList.BoundaryCallback<NowPlayingMovie> {
    private AppExecutors executors;
    private Database database;
    private NowPlayingMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<NowPlayingMoviesPage> current_movie_page;

    public NowPlayingMoviesBoundaryCallback(Application application, AppExecutors executors) {
        //super();
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_now_playing_movies_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        //Log.d(Constants.LOG, "nowPlayingMovies onzeroitemsloaded");
        fetchNowPlayingMovies(NOW_PLAYING_MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull NowPlayingMovie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d(Constants.LOG, "nowPlayingMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull NowPlayingMovie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_movie_page.getValue().getPage() + 1;
        //Log.d(Constants.LOG, "nowPlayingMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchNowPlayingMovies(page_number);
    }

    public void fetchNowPlayingMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        //Call<NowPlayingMoviesPage> call = theMovieDbApi.getNowPlayingMovies(TMDB_API_KEY, pageNumber, REGION);
        Call<NowPlayingMoviesPage> call = theMovieDbApi.getNowPlayingMovies(TMDB_API_KEY, pageNumber, "");
        call.enqueue(new Callback<NowPlayingMoviesPage>() {
            @Override
            public void onResponse(Call<NowPlayingMoviesPage> call, Response<NowPlayingMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d(Constants.LOG, "nowPlayingMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d(Constants.LOG, "nowPlayingMovies Response ok: " + response.code());
                NowPlayingMoviesPage mNowPlayingMovies = response.body();
                insertListToDb(mNowPlayingMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<NowPlayingMoviesPage> call, Throwable t) {
                Log.d(Constants.LOG, "nowPlayingMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }


    public void insertListToDb(NowPlayingMoviesPage page) {
        List<NowPlayingMovie> listOfResults = page.getResults_list();
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

    public LiveData<NowPlayingMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }


}
