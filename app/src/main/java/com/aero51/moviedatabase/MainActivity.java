package com.aero51.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;

    private TheMovieDbApi theMovieDbApi;
    private TopRatedMoviesAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TextView textView;
    private List<Top_Rated_Movies> top_rated_movies_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new TopRatedMoviesAdapter();
        recyclerView.setAdapter(adapter);
        textView = findViewById(R.id.text_view_top_rated_movies);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("moviedatabaselog", "EndlessRecyclerViewScrollListener   page: " + page + " total items count: " + totalItemsCount);
                getTopRatedMovies(page + 1);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        theMovieDbApi = retrofit.create(TheMovieDbApi.class);
        getTopRatedMovies(TOP_RATED_MOVIES_FIRST_PAGE);
    }

    private void loadNextDataFromApi(int page) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

    }

    private void getTopRatedMovies(int page) {
        Call<Top_Rated_Movies> call = theMovieDbApi.getTopRatedMovies(API_KEY, page);
        call.enqueue(new Callback<Top_Rated_Movies>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies> call, Response<Top_Rated_Movies> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Response unsuccesful: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    return;
                }
                Top_Rated_Movies mTopRatedMovies = response.body();
                top_rated_movies_list.add(mTopRatedMovies);

                Log.d("moviedatabaselog", "Response succesful: " + response.code() + " " + mTopRatedMovies.getResults_list().size());
                String text = " Total pages: " + mTopRatedMovies.getTotal_pages() + " Total reults: " + mTopRatedMovies.getTotal_results();
                textView.setText(text);


                adapter.setResults(mTopRatedMovies.getResults_list());
                Log.d("moviedatabaselog", "adapter.setResults: adapter.getItemCount():  " + adapter.getItemCount() + " ,page: " + page);

                if (page == 1) {
                    adapter.notifyDataSetChanged();
                } else {
                    int position_start = adapter.getItemCount() + 1;
                    int itemCount = mTopRatedMovies.getResults_list().size();
                    adapter.notifyItemRangeInserted(position_start, itemCount);
                }


            }

            @Override
            public void onFailure(Call<Top_Rated_Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("moviedatabaselog", "onFailure: " + t.getMessage());
            }
        });

    }
}

/*
        poster
        https://image.tmdb.org/t/p/w92/5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg

        backdrop
        w300
        /avedvodAZUcwqevBfm8p4G2NziQ.jpg
        https://image.tmdb.org/t/p/w300/avedvodAZUcwqevBfm8p4G2NziQ.jpg


 */