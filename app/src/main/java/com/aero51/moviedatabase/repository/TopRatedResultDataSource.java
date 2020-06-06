package com.aero51.moviedatabase.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class TopRatedResultDataSource extends PageKeyedDataSource<Integer, Top_Rated_Result> {
    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Top_Rated_Result> callback) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, TOP_RATED_MOVIES_FIRST_PAGE);
        Log.d("moviedatabaselog", "load initial ");
        List<Top_Rated_Result> list_of_results= fetchTopRatedMovies(call);
        callback.onResult(list_of_results,null,TOP_RATED_MOVIES_FIRST_PAGE+1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Top_Rated_Result> callback) {
        Log.d("moviedatabaselog", "Load before: " + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Top_Rated_Result> callback) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, params.key);

        Log.d("moviedatabaselog", "load after ");
        List<Top_Rated_Result> list_of_results= fetchTopRatedMovies(call);
            callback.onResult(list_of_results, params.key + 1);
    }

    private List<Top_Rated_Result> fetchTopRatedMovies(Call<Top_Rated_Movies_Page> call){
        List<Top_Rated_Result> list_of_results = new ArrayList<>();
        try {
            Response<Top_Rated_Movies_Page> response = call.execute();
            if (!response.isSuccessful()) {
                Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                return null;
            }
            Top_Rated_Movies_Page mTopRatedMovies = response.body();
            Log.d("moviedatabaselog", "load initial ");
             list_of_results = mTopRatedMovies.getResults_list();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("moviedatabaselog", "call failure  IOException : " + e.getMessage());
        }
        return list_of_results;

    }

}
