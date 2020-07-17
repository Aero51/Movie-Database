package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.TopRatedMoviesDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;
import static com.aero51.moviedatabase.utils.Constants.REGION;
import static com.aero51.moviedatabase.utils.Constants.TOP_RATED_MOVIES_FIRST_PAGE;

public class TopRatedMoviesBoundaryCallback extends PagedList.BoundaryCallback<TopRatedMovie> {
    private AppExecutors executors;
    private Database database;
    private TopRatedMoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private LiveData<TopRatedMoviesPage> current_movie_page;

    public TopRatedMoviesBoundaryCallback(Application application, AppExecutors executors) {
        //super();
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_top_rated_movies_dao();
        networkState = new MutableLiveData<>();
        current_movie_page = dao.getLiveDataMoviePage();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.d("moviedatabaselog", "topRatedMovies onzeroitemsloaded");
        fetchTopRatedMovies(TOP_RATED_MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull TopRatedMovie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d("moviedatabaselog", "topRatedMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull TopRatedMovie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = current_movie_page.getValue().getPage() + 1;
        Log.d("moviedatabaselog", "topRatedMovies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchTopRatedMovies(page_number);
    }

    public void fetchTopRatedMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<TopRatedMoviesPage> call = theMovieDbApi.getTopRatedMovies(API_KEY, pageNumber, REGION);
        call.enqueue(new Callback<TopRatedMoviesPage>() {
            @Override
            public void onResponse(Call<TopRatedMoviesPage> call, Response<TopRatedMoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "topRatedMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "topRatedMovies Response ok: " + response.code());
                TopRatedMoviesPage mTopRatedMovies = response.body();
                insertListToDb(mTopRatedMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<TopRatedMoviesPage> call, Throwable t) {
                Log.d("moviedatabaselog", "topRatedMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }


    public void insertListToDb(TopRatedMoviesPage page) {
        List<TopRatedMovie> listOfResults = page.getResults_list();
        Runnable runnable = () -> {
            dao.deleteAllMoviePages();
            dao.insertMoviePage(page);
            for(TopRatedMovie movie:listOfResults){
                movie.setCollectionsName("TopRated");
                movie.setPage_number(page.getPage());
            }

            dao.insertList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<TopRatedMoviesPage> getCurrent_movie_page() {
        return current_movie_page;
    }


}
