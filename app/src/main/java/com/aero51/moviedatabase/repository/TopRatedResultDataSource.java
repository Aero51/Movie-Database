package com.aero51.moviedatabase.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedResultDataSource extends PageKeyedDataSource<Integer, Top_Rated_Result> {
    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;
    public static final int  PAGE_SIZE=20;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Top_Rated_Result> callback) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, TOP_RATED_MOVIES_FIRST_PAGE);
        call.enqueue(new Callback<Top_Rated_Movies_Page>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies_Page> call, Response<Top_Rated_Movies_Page> response) {
                if (!response.isSuccessful()) {

                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    return;
                }
                Top_Rated_Movies_Page mTopRatedMovies = response.body();
                Log.d("moviedatabaselog", "load initial ");
                List<Top_Rated_Result> list_of_results = mTopRatedMovies.getResults_list();
                callback.onResult(list_of_results, null, TOP_RATED_MOVIES_FIRST_PAGE + 1);
            }

            @Override
            public void onFailure(Call<Top_Rated_Movies_Page> call, Throwable t) {

                Log.d("moviedatabaselog", "load initial onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Top_Rated_Result> callback) {

        Log.d("moviedatabaselog", "Load before: " + params.key);

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Top_Rated_Result> callback) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, params.key);
        call.enqueue(new Callback<Top_Rated_Movies_Page>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies_Page> call, Response<Top_Rated_Movies_Page> response) {
                if (!response.isSuccessful()) {

                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    return;
                }
                Top_Rated_Movies_Page mTopRatedMovies = response.body();

                List<Top_Rated_Result> list_of_results = mTopRatedMovies.getResults_list();
                //incrementing the next page number

                Log.d("moviedatabaselog", "Load after:params key:  " + params.key);
                callback.onResult(list_of_results, params.key+1);
            }

            @Override
            public void onFailure(Call<Top_Rated_Movies_Page> call, Throwable t) {

                Log.d("moviedatabaselog", "Load after onFailure: " + t.getMessage());
            }
        });
    }
/*
    public void getTopRatedMovies(int page) {
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, page);
        call.enqueue(new Callback<Top_Rated_Movies_Page>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies_Page> call, Response<Top_Rated_Movies_Page> response) {
                if (!response.isSuccessful()) {

                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    return;
                }
                Top_Rated_Movies_Page mTopRatedMovies = response.body();

                List<Top_Rated_Result> list_of_results=mTopRatedMovies.getResults_list();
                top_rated_result_list.addAll(list_of_results);
                allResults.setValue(list_of_results);
            }

            @Override
            public void onFailure(Call<Top_Rated_Movies_Page> call, Throwable t) {

                Log.d("moviedatabaselog", "onFailure: " + t.getMessage());
            }
        });

    }
    */

}
