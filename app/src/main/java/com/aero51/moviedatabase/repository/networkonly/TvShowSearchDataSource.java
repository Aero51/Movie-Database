package com.aero51.moviedatabase.repository.networkonly;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowSearchResult;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class TvShowSearchDataSource extends PageKeyedDataSource<Integer, TvShowSearchResult.TvShow> {
    public static final int TV_SHOW_SEARCH_FIRST_PAGE = 1;
    private MutableLiveData networkState;
    private String queryString;

    public TvShowSearchDataSource(String queryString) {
        networkState = new MutableLiveData();
        this.queryString = queryString;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TvShowSearchResult.TvShow> callback) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

        Call<TvShowSearchResult> call = theMovieDbApi.getTvShowsSearch(TMDB_API_KEY, queryString, TV_SHOW_SEARCH_FIRST_PAGE);
        Log.d(Constants.LOG, "load initial TvShowSearchDataSource");
        List<TvShowSearchResult.TvShow> list_of_results = searchTvShows(call);
        callback.onResult(list_of_results, null, TV_SHOW_SEARCH_FIRST_PAGE + 1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TvShowSearchResult.TvShow> callback) {
        Log.d(Constants.LOG, "Load before: " + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TvShowSearchResult.TvShow> callback) {

        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

        Call<TvShowSearchResult> call = theMovieDbApi.getTvShowsSearch(TMDB_API_KEY, queryString, params.key);
        Log.d(Constants.LOG, "load after TvShowSearchDataSource:params.key " + params.key);
        List<TvShowSearchResult.TvShow> list_of_results = searchTvShows(call);
        callback.onResult(list_of_results, params.key + 1);
    }


    private List<TvShowSearchResult.TvShow> searchTvShows(Call<TvShowSearchResult> call) {
        List<TvShowSearchResult.TvShow> list_of_results = new ArrayList<>();
        try {
            Response<TvShowSearchResult> response = call.execute();
            if (!response.isSuccessful()) {
                Log.d(Constants.LOG, "Response unsuccesful: " + response.code());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                return null;
            }
            networkState.postValue(NetworkState.LOADED);
            TvShowSearchResult tvShowSearchResult = response.body();
            list_of_results = tvShowSearchResult.getResults();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(Constants.LOG, "call failure  IOException : " + e.getMessage());
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return list_of_results;

    }
}
