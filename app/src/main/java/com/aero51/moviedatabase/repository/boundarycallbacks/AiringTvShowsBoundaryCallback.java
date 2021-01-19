package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.AiringTvShowsDao;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
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
import static com.aero51.moviedatabase.utils.Constants.TV_SHOWS_FIRST_PAGE;

public class AiringTvShowsBoundaryCallback extends PagedList.BoundaryCallback<AiringTvShowsPage.AiringTvShow>{

    private AppExecutors executors;
    private Database database;
    private AiringTvShowsDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<AiringTvShowsPage> current_tv_shows_page;

    public AiringTvShowsBoundaryCallback(Application application, AppExecutors executors) {
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_airing_tv_shows_dao();
        networkState = new MutableLiveData<>();
        current_tv_shows_page = dao.getLiveDataAiringTvShowPage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        //Log.d(Constants.LOG, "popular tv shows onzeroitemsloaded");
        fetchPopularTvShows(TV_SHOWS_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull AiringTvShowsPage.AiringTvShow itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d(Constants.LOG, "airing tv shows onItemAtFrontLoaded,item:" + itemAtFront.getName());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull AiringTvShowsPage.AiringTvShow itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_tv_shows_page.getValue().getPage() + 1;
        //Log.d(Constants.LOG, "popular tv shows onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchPopularTvShows(page_number);
    }
    public void fetchPopularTvShows(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<AiringTvShowsPage> call = theMovieDbApi.getAiringTvShows(TMDB_API_KEY, pageNumber,REGION);
        call.enqueue(new Callback<AiringTvShowsPage>() {
            @Override
            public void onResponse(Call<AiringTvShowsPage> call, Response<AiringTvShowsPage> response) {
                if (!response.isSuccessful()) {
                    Log.d(Constants.LOG, "airing tv shows Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d(Constants.LOG, "airing tv shows Response ok: " + response.code());
                AiringTvShowsPage mPopularTvShows = response.body();
                insertListToDb(mPopularTvShows);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<AiringTvShowsPage> call, Throwable t) {
                Log.d(Constants.LOG, "airing tv shows onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    public void insertListToDb(AiringTvShowsPage page) {
        List<AiringTvShowsPage.AiringTvShow> listOfResults = page.getResults();

        Runnable runnable = () -> {
            dao.deleteAllAiringTvShowPages();
            dao.insertAiringTvShowPage(page);
            dao.insertList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<AiringTvShowsPage> getCurrent_Tv_shows_page() {
        return current_tv_shows_page;
    }

}
