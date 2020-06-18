package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.db.PopularMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.repository.model.PopularMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;
import static com.aero51.moviedatabase.utils.Constants.POPULAR_MOVIES_FIRST_PAGE;
import static com.aero51.moviedatabase.utils.Constants.REGION;

public class PopularMoviesRepository {

    private MoviesDatabase database;
    private PopularMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;

    private LiveData<PagedList<PopularMovie>> popularMoviesPagedList;
    private PagedList.BoundaryCallback<PopularMovie> popularMoviesBoundaryCallback;

    private LiveData<PopularMoviesPage> current_movie_page;

    public PopularMoviesRepository(Application application) {
        database = MoviesDatabase.getInstance(application);
        dao = database.get_popular_movies_dao();

        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();

        popularMoviesBoundaryCallback = new PagedList.BoundaryCallback<PopularMovie>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.d("moviedatabaselog", "popularMovies onzeroitemsloaded");

                fetchPopularMovies(POPULAR_MOVIES_FIRST_PAGE);
            }

            @Override
            public void onItemAtFrontLoaded(@NonNull PopularMovie itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                Log.d("moviedatabaselog", "popularMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
            }

            @Override
            public void onItemAtEndLoaded(@NonNull PopularMovie itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                Integer page_number = current_movie_page.getValue().getPage() + 1;
                Log.d("moviedatabaselog", "popularMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
                fetchPopularMovies(page_number);
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
        popularMoviesPagedList = new LivePagedListBuilder<>(dao.getAllResults(), pagedListConfig)
                .setBoundaryCallback(popularMoviesBoundaryCallback).setFetchExecutor(executor)
                .build();
    }

    public void fetchPopularMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<PopularMoviesPage> call = theMovieDbApi.getPopularMovies(API_KEY, pageNumber, REGION);
        call.enqueue(new Callback<PopularMoviesPage>() {
            @Override
            public void onResponse(Call<PopularMoviesPage> call, Response<PopularMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "popularMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "popularMovies Response ok: " + response.code());
                PopularMoviesPage mPopularMovies = response.body();
                insertListToDb(mPopularMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<PopularMoviesPage> call, Throwable t) {
                Log.d("moviedatabaselog", "popularMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    public LiveData<PopularMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }

    public void insertListToDb(PopularMoviesPage page) {
        new InsertListOfResultsAsyncTask(database, dao).execute(page);
    }

    private static class InsertListOfResultsAsyncTask extends AsyncTask<PopularMoviesPage, Void, Void> {
        private MoviesDatabase database;
        private PopularMoviesDao popularMoviesDao;


        private InsertListOfResultsAsyncTask(MoviesDatabase database, PopularMoviesDao popularMoviesDao) {
            this.database = database;
            this.popularMoviesDao = popularMoviesDao;
        }

        @Override
        protected Void doInBackground(PopularMoviesPage... popular_page) {
            PopularMoviesPage page = popular_page[0];
            List<PopularMovie> listOfResults = page.getResults_list();
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    popularMoviesDao.deleteAllMoviePages();
                    popularMoviesDao.insertMoviePage(page);
                    popularMoviesDao.insertList(listOfResults);
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

    public LiveData<PagedList<PopularMovie>> getPopularResultsPagedList() {
        return popularMoviesPagedList;
    }
}
