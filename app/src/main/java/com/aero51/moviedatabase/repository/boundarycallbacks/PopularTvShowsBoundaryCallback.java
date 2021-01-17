package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.PopularTvShowsDao;
import com.aero51.moviedatabase.repository.model.NetworkState;

import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;
import static com.aero51.moviedatabase.utils.Constants.TV_SHOWS_FIRST_PAGE;

public class PopularTvShowsBoundaryCallback extends PagedList.BoundaryCallback<PopularTvShowsPage.PopularTvShow>{
    private AppExecutors executors;
    private Database database;
    private PopularTvShowsDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<PopularTvShowsPage> current_tv_shows_page;

    public PopularTvShowsBoundaryCallback(Application application, AppExecutors executors) {
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_popular_tv_shows_dao();
        networkState = new MutableLiveData<>();
        current_tv_shows_page = dao.getLiveDataTvShowPage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        //Log.d(Constants.LOG, "popular tv shows onzeroitemsloaded");
        fetchPopularTvShows(TV_SHOWS_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull PopularTvShowsPage.PopularTvShow itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d(Constants.LOG, "popular tv shows onItemAtFrontLoaded,item:" + itemAtFront.getName());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull PopularTvShowsPage.PopularTvShow itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_tv_shows_page.getValue().getPage() + 1;
        //Log.d(Constants.LOG, "popular tv shows onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularTvShows(page_number);
    }
    public void fetchPopularTvShows(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        //Call<PopularMoviesPage> call = theMovieDbApi.getPopularMovies(TMDB_API_KEY, pageNumber, REGION);
        Call<PopularTvShowsPage> call = theMovieDbApi.getPopularTvShows(TMDB_API_KEY, pageNumber,"");
        call.enqueue(new Callback<PopularTvShowsPage>() {
            @Override
            public void onResponse(Call<PopularTvShowsPage> call, Response<PopularTvShowsPage> response) {
                if (!response.isSuccessful()) {
                    Log.d(Constants.LOG, "popular tv shows Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d(Constants.LOG, "popular tv shows Response ok: " + response.code());
                PopularTvShowsPage mPopularTvShows = response.body();
                insertListToDb(mPopularTvShows);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<PopularTvShowsPage> call, Throwable t) {
                Log.d(Constants.LOG, "popular tv shows onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    public void insertListToDb(PopularTvShowsPage page) {
        List<PopularTvShowsPage.PopularTvShow> listOfResults = page.getResults();

        Runnable runnable = () -> {
            dao.deleteAllTvShowPages();
            dao.insertTvShowPage(page);
            dao.insertList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PopularTvShowsPage> getCurrent_Tv_shows_page() {
        return current_tv_shows_page;
    }
}
