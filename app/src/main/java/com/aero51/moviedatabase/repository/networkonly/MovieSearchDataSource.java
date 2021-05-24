package com.aero51.moviedatabase.repository.networkonly;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieSearchResult;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class MovieSearchDataSource extends PageKeyedDataSource<Integer, NowPlayingMoviesPage.NowPlayingMovie> {
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NowPlayingMoviesPage.NowPlayingMovie> callback) {

        networkState.postValue(NetworkState.LOADING);

        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

            Call<MovieSearchResult> call = theMovieDbApi.getMoviesSearch(TMDB_API_KEY, queryString, MOVIES_SEARCH_FIRST_PAGE);
            List<NowPlayingMoviesPage.NowPlayingMovie> list_of_results = searchMovies(call);
            callback.onResult(list_of_results, null, MOVIES_SEARCH_FIRST_PAGE + 1);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NowPlayingMoviesPage.NowPlayingMovie> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NowPlayingMoviesPage.NowPlayingMovie> callback) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();

            Call<MovieSearchResult> call = theMovieDbApi.getMoviesSearch(TMDB_API_KEY, queryString, params.key);
            List<NowPlayingMoviesPage.NowPlayingMovie> list_of_results = searchMovies(call);
            callback.onResult(list_of_results, params.key + 1);

    }

    private List<NowPlayingMoviesPage.NowPlayingMovie> searchMovies(Call<MovieSearchResult> call) {
        List<NowPlayingMoviesPage.NowPlayingMovie> list_of_results = new ArrayList<>();
        try {
            Response<MovieSearchResult> response = call.execute();
            if (!response.isSuccessful()) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                return null;
            }
            networkState.postValue(NetworkState.LOADED);
            MovieSearchResult movieSearchResult = response.body();
            list_of_results = movieSearchResult.getResults();
        } catch (IOException e) {
            e.printStackTrace();
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return list_of_results;

    }

}