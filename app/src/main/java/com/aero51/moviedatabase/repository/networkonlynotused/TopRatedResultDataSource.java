package com.aero51.moviedatabase.repository.networkonlynotused;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TopRatedResultDataSource extends PageKeyedDataSource<Integer, TopRatedMovie> {
    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public TopRatedResultDataSource() {
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TopRatedMovie> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<TopRatedMoviesPage> call = theMovieDbApi.getTopRatedMovies(API_KEY, TOP_RATED_MOVIES_FIRST_PAGE,"us");
        Log.d("moviedatabaselog", "load initial ");
        List<TopRatedMovie> list_of_results = fetchTopRatedMovies(call);
        callback.onResult(list_of_results, null, TOP_RATED_MOVIES_FIRST_PAGE + 1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TopRatedMovie> callback) {
        Log.d("moviedatabaselog", "Load before: " + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TopRatedMovie> callback) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<TopRatedMoviesPage> call = theMovieDbApi.getTopRatedMovies(API_KEY, params.key,"us");
        Log.d("moviedatabaselog", "load after:params.key " + params.key);
        List<TopRatedMovie> list_of_results = fetchTopRatedMovies(call);
        callback.onResult(list_of_results, params.key + 1);
    }

    private List<TopRatedMovie> fetchTopRatedMovies(Call<TopRatedMoviesPage> call) {
        List<TopRatedMovie> list_of_results = new ArrayList<>();
        try {
            Response<TopRatedMoviesPage> response = call.execute();
            if (!response.isSuccessful()) {
                Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                return null;
            }
            networkState.postValue(NetworkState.LOADED);
            TopRatedMoviesPage mTopRatedMovies = response.body();
            list_of_results = mTopRatedMovies.getResults_list();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("moviedatabaselog", "call failure  IOException : " + e.getMessage());
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return list_of_results;

    }

}
