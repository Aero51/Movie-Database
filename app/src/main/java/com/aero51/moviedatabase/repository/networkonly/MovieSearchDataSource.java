package com.aero51.moviedatabase.repository.networkonly;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieSearchResult;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class MovieSearchDataSource extends PageKeyedDataSource<Integer, TopRatedMovie> {
    public static final int MOVIES_SEARCH_FIRST_PAGE = 1;

    private MutableLiveData networkState;
    private String queryString;


    public MovieSearchDataSource(String query) {

        networkState = new MutableLiveData();
        this.queryString = query;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TopRatedMovie> callback) {

        networkState.postValue(NetworkState.LOADING);

        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

            Call<MovieSearchResult> call = theMovieDbApi.getMoviesSearch(TMDB_API_KEY, queryString, MOVIES_SEARCH_FIRST_PAGE);
            Log.d(Constants.LOG, "load initial ");
            List<TopRatedMovie> list_of_results = searchMovies(call);
            callback.onResult(list_of_results, null, MOVIES_SEARCH_FIRST_PAGE + 1);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TopRatedMovie> callback) {
        Log.d(Constants.LOG, "Load before: " + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TopRatedMovie> callback) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

            Call<MovieSearchResult> call = theMovieDbApi.getMoviesSearch(TMDB_API_KEY, queryString, params.key);
            Log.d(Constants.LOG, "load after:params.key " + params.key);
            List<TopRatedMovie> list_of_results = searchMovies(call);
            callback.onResult(list_of_results, params.key + 1);

    }

    private List<TopRatedMovie> searchMovies(Call<MovieSearchResult> call) {
        List<TopRatedMovie> list_of_results = new ArrayList<>();
        try {
            Response<MovieSearchResult> response = call.execute();
            if (!response.isSuccessful()) {
                Log.d(Constants.LOG, "Response unsuccesful: " + response.code());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                return null;
            }
            networkState.postValue(NetworkState.LOADED);
            MovieSearchResult movieSearchResult = response.body();
            list_of_results = movieSearchResult.getResults();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(Constants.LOG, "call failure  IOException : " + e.getMessage());
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return list_of_results;

    }

}