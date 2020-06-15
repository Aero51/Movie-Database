package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Rated_Results_Repository {

    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;

    private Top_Rated_Results_Database database;
    private MutableLiveData<NetworkState> networkState;
    private Top_Rated_Result_Dao dao;

    private LiveData<PagedList<Top_Rated_Result>> newTopRatedResultsPagedList;
    private PagedList.BoundaryCallback<Top_Rated_Result> newBoundaryCallback;
    private Integer page_number;



    private LiveData<Top_Rated_Movies_Page> current_movie_page;

    public Top_Rated_Results_Repository(Application application) {
        database = Top_Rated_Results_Database.getInstance(application);
        dao = database.get_top_rated_results_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();

        newBoundaryCallback = new PagedList.BoundaryCallback<Top_Rated_Result>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.d("moviedatabaselog", "onzeroitemsloaded");

                fetchTopRatedMovies(TOP_RATED_MOVIES_FIRST_PAGE);
            }

            @Override
            public void onItemAtFrontLoaded(@NonNull Top_Rated_Result itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                Log.d("moviedatabaselog", "onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
            }

            @Override
            public void onItemAtEndLoaded(@NonNull Top_Rated_Result itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                page_number = current_movie_page.getValue().getPage() + 1;
                Log.d("moviedatabaselog", "onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
                fetchTopRatedMovies(page_number);
            }

        };

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(40)
                        .setInitialLoadSizeHint(60)
                        .setPageSize(20).build();

        Executor executor = Executors.newFixedThreadPool(5);
        newTopRatedResultsPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(newBoundaryCallback).setFetchExecutor(executor)
                .build();
    }

    public void fetchTopRatedMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, pageNumber);
        call.enqueue(new Callback<Top_Rated_Movies_Page>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies_Page> call, Response<Top_Rated_Movies_Page> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "Response ok: " + response.code());
                Top_Rated_Movies_Page mTopRatedMovies = response.body();
                insertListToDb(mTopRatedMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<Top_Rated_Movies_Page> call, Throwable t) {
                Log.d("moviedatabaselog", "onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }



    public LiveData<Top_Rated_Movies_Page> getCurrent_movie_page() {
        return current_movie_page;
    }

    public void insertListToDb(Top_Rated_Movies_Page page) {
        new InsertListOfResultsAsyncTask(dao).execute(page);
    }

    private static class InsertListOfResultsAsyncTask extends AsyncTask<Top_Rated_Movies_Page, Void, Void> {
        private Top_Rated_Result_Dao top_rated_result_dao;

        private InsertListOfResultsAsyncTask(Top_Rated_Result_Dao top_rated_result_dao) {
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(Top_Rated_Movies_Page... top_rated_page) {
            Top_Rated_Movies_Page page = top_rated_page[0];
            List<Top_Rated_Result> listOfResults = page.getResults_list();
            //perhaps implement run in transaction
            top_rated_result_dao.deleteAllMoviePages();
            top_rated_result_dao.insertMoviePage(page);
            top_rated_result_dao.insertList(listOfResults);
            return null;
        }
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Top_Rated_Result>> getTopRatedResultsPagedList() {
        return newTopRatedResultsPagedList;
    }


}