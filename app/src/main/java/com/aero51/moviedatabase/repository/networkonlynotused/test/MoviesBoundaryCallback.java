package com.aero51.moviedatabase.repository.networkonlynotused.test;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aero51.moviedatabase.utils.Constants.API_KEY;
import static com.aero51.moviedatabase.utils.Constants.MOVIES_FIRST_PAGE;
import static com.aero51.moviedatabase.utils.Constants.REGION;
import static com.aero51.moviedatabase.utils.Constants.TOP_RATED_MOVIE_TYPE_ID;

public class MoviesBoundaryCallback extends PagedList.BoundaryCallback<Movie> {
    private AppExecutors executors;
    private MoviesDatabase database;
    private MoviesDao dao;
    private MutableLiveData<NetworkState> networkState;
    private Integer movie_type_id;


    public MoviesBoundaryCallback(Application application, AppExecutors executors, Integer movie_type_id) {
        // super();
        this.executors = executors;
        database = MoviesDatabase.getInstanceAllowOnMainThread(application);
      //  dao = database.get_movies_dao();
        networkState = new MutableLiveData<>();
        this.movie_type_id = movie_type_id;

    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.d("moviedatabaselog", "popularMovies onzeroitemsloaded");
        fetchMovies(MOVIES_FIRST_PAGE);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Movie itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d("moviedatabaselog", "popularMovies onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Integer page_number = 0;
        if (movie_type_id == TOP_RATED_MOVIE_TYPE_ID) {
            page_number = dao.getTopRatedPage() + 1;
        } else {
            page_number = dao.getPopularPage() + 1;
        }
        Log.d("moviedatabaselog", "top Movies onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,page: " + page_number);
        fetchMovies(page_number);
    }

    public void fetchMovies(int pageNumber) {
        networkState.postValue(NetworkState.LOADING);
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        Call<MoviesPage> call = null;
        if (movie_type_id == TOP_RATED_MOVIE_TYPE_ID) {
          //  call = theMovieDbApi.getNewTopRatedMovies(API_KEY, pageNumber, REGION);
        } else {
          //  call = theMovieDbApi.getNewPopularMovies(API_KEY, pageNumber, REGION);
        }

   /*     call.enqueue(new Callback<MoviesPage>() {
            @Override
            public void onResponse(Call<MoviesPage> call, Response<MoviesPage> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "popularMovies Response unsuccesful: " + response.code());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "popularMovies Response ok: " + response.code());
               // MoviesPage mPopularMovies = response.body();
               // insertListToDb(mPopularMovies);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<MoviesPage> call, Throwable t) {
                Log.d("moviedatabaselog", "popularMovies onFailure: " + t.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
        */

    }

    public void insertListToDb(MoviesPage page) {
        List<Movie> listOfResults = page.getResults();
        Integer pageId = page.getPage();

        Runnable runnable = () -> {

            Movie tempMovie = listOfResults.get(0);
           // Mov mov =tempMovie;



            //Log.d("moviedatabaselog", "TopMovie topMovie: " + mov.getTitle());
            for (Movie movie : listOfResults) {
                movie.setMovieTypeId(movie_type_id);
            }
            if (movie_type_id == TOP_RATED_MOVIE_TYPE_ID) {
                Log.d("moviedatabaselog", "insertListToDb: TOP_RATED_MOVIE_TYPE_ID");
                TopRatedPage topRatedPage = new TopRatedPage(pageId);
                dao.deleteTopRatedMoviePage();
               // dao.insertTopRatedPage(topRatedPage);
            } else {
                PopularPage popularPage = new PopularPage(pageId);
                dao.deletePopularMoviePage();
              //  dao.insertPopularPage(popularPage);
            }

            dao.inserMoviesList(listOfResults);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
