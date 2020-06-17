package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMoviesRepository {

    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;

    private MoviesDatabase database;
    private TopRatedMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;

    private LiveData<PagedList<TopRatedMovie>> topRatedMoviesPagedList;
    private PagedList.BoundaryCallback<TopRatedMovie> topRatedMoviesBoundaryCallback;

    private LiveData<TopRatedMoviesPage> current_movie_page;

    public TopRatedMoviesRepository(Application application) {
        database = MoviesDatabase.getInstance(application);
        dao = database.get_top_rated_results_dao();

        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();

        topRatedMoviesBoundaryCallback = new PagedList.BoundaryCallback<TopRatedMovie>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.d("moviedatabaselog", "onzeroitemsloaded");

                fetchTopRatedMovies(TOP_RATED_MOVIES_FIRST_PAGE);
            }

            @Override
            public void onItemAtFrontLoaded(@NonNull TopRatedMovie itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                Log.d("moviedatabaselog", "onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
            }

            @Override
            public void onItemAtEndLoaded(@NonNull TopRatedMovie itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                Integer page_number = current_movie_page.getValue().getPage() + 1;
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
        topRatedMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(topRatedMoviesBoundaryCallback).setFetchExecutor(executor)
                .build();
    }

    public void fetchTopRatedMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<TopRatedMoviesPage> call = theMovieDbApi.getTopRatedMovies(API_KEY, pageNumber, "us");
        call.enqueue(new Callback<TopRatedMoviesPage>() {
            @Override
            public void onResponse(Call<TopRatedMoviesPage> call, Response<TopRatedMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "Response ok: " + response.code());
                TopRatedMoviesPage mTopRatedMovies = response.body();
                insertListToDb(mTopRatedMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<TopRatedMoviesPage> call, Throwable t) {
                Log.d("moviedatabaselog", "onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    public LiveData<TopRatedMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }

    public void insertListToDb(TopRatedMoviesPage page) {
        new InsertListOfResultsAsyncTask(database, dao).execute(page);
    }

    private static class InsertListOfResultsAsyncTask extends AsyncTask<TopRatedMoviesPage, Void, Void> {
        private MoviesDatabase database;
        private TopRatedMoviesDao top_rated_result_dao;


        private InsertListOfResultsAsyncTask(MoviesDatabase database, TopRatedMoviesDao top_rated_result_dao) {
            this.database = database;
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(TopRatedMoviesPage... top_rated_page) {
            TopRatedMoviesPage page = top_rated_page[0];
            List<TopRatedMovie> listOfResults = page.getResults_list();
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    top_rated_result_dao.deleteAllMoviePages();
                    top_rated_result_dao.insertMoviePage(page);
                    top_rated_result_dao.insertList(listOfResults);
                }
            });
            return null;
        }
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<TopRatedMovie>> getTopRatedResultsPagedList() {
        return topRatedMoviesPagedList;
    }
}