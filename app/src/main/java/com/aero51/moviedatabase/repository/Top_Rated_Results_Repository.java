package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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

    //creating livedata for PagedList
    private MutableLiveData<NetworkState> networkState;
    private Top_Rated_Result_Dao dao;

    private LiveData<PagedList<Top_Rated_Result>> newTopRatedResultsPagedList;
    private PagedList.BoundaryCallback<Top_Rated_Result> newBoundaryCallback;



    private LiveData<Top_Rated_Movies_Page> moviePageLd;
    private Integer page_number;



    private Observer<Top_Rated_Movies_Page> observer;

    public Top_Rated_Results_Repository(Application application) {
        Top_Rated_Results_Database database = Top_Rated_Results_Database.getInstance(application);
        dao = database.get_top_rated_results_dao();
        networkState = new MutableLiveData<>();


        newBoundaryCallback = new PagedList.BoundaryCallback<Top_Rated_Result>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.d("moviedatabaselog", "onzeroitemsloaded");

                if (page_number == null) {
                    page_number = TOP_RATED_MOVIES_FIRST_PAGE;
                }
                fetchTopRatedMovies(page_number);

            }

            @Override
            public void onItemAtFrontLoaded(@NonNull Top_Rated_Result itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                Log.d("moviedatabaselog", "onItemAtFrontLoaded,item:" + itemAtFront.getTitle());
            }

            @Override
            public void onItemAtEndLoaded(@NonNull Top_Rated_Result itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                Log.d("moviedatabaselog", "onItemAtEndLoaded,item:" + itemAtEnd.getTitle() + " ,pageNmbr: " + page_number);
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
        newTopRatedResultsPagedList = new LivePagedListBuilder<>(dao.getAllResultsNew(), pagedListConfig)
                .setBoundaryCallback(newBoundaryCallback).setFetchExecutor(executor)
                .build();

        observer= new Observer<Top_Rated_Movies_Page>() {
            @Override
            public void onChanged(Top_Rated_Movies_Page top_rated_movies_page) {
                if (top_rated_movies_page == null) {
                    page_number = TOP_RATED_MOVIES_FIRST_PAGE;
                }else{
                    page_number=top_rated_movies_page.getPage()+1;
                }

                Log.d("moviedatabaselog", "observeForever page number: " + page_number);
            }
        };
        moviePageLd=dao.getMoviePage();
        moviePageLd.observeForever(observer);

    }
    public Observer<Top_Rated_Movies_Page> getObserver() {
        return observer;
    }
    public LiveData<Top_Rated_Movies_Page> getMoviePageLd() {
        return moviePageLd;
    }

    public void fetchTopRatedMovies(int pageNumber) {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getApiService();
        Call<Top_Rated_Movies_Page> call = theMovieDbApi.getTopRatedMovies(API_KEY, pageNumber);
        call.enqueue(new Callback<Top_Rated_Movies_Page>() {
            @Override
            public void onResponse(Call<Top_Rated_Movies_Page> call, Response<Top_Rated_Movies_Page> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "Response unsuccesful: " + response.code());
                    return;
                }
                Log.d("moviedatabaselog", "Response ok: " + response.code());
                Top_Rated_Movies_Page mTopRatedMovies = response.body();
                insertListToDb(mTopRatedMovies);

            }

            @Override
            public void onFailure(Call<Top_Rated_Movies_Page> call, Throwable t) {
                Log.d("moviedatabaselog", "onFailure: " + t.getMessage());
            }
        });
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Top_Rated_Result>> getNewTopRatedResultsPagedList() {
        return newTopRatedResultsPagedList;
    }

    public void insertListToDb(Top_Rated_Movies_Page page) {
        new InsertListOfResultsAsyncTask(dao).execute(page);
    }

    public void insert(Top_Rated_Result top_rated_result) {
        new InsertNoteAsyncTask(dao).execute(top_rated_result);
    }

    public void update(Top_Rated_Result top_rated_result) {
        new UpdateNoteAsyncTask(dao).execute(top_rated_result);
    }

    public void delete(Top_Rated_Result top_rated_result) {
        new DeleteNoteAsyncTask(dao).execute(top_rated_result);
    }

    public void deleteAllResults() {
        new DeleteAllNotesAsyncTask(dao).execute();
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
            top_rated_result_dao.insertList(page, listOfResults);
            return null;
        }
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Top_Rated_Result, Void, Void> {
        private Top_Rated_Result_Dao top_rated_result_dao;

        private InsertNoteAsyncTask(Top_Rated_Result_Dao top_rated_result_dao) {
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(Top_Rated_Result... results) {
            top_rated_result_dao.insert(results[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Top_Rated_Result, Void, Void> {
        private Top_Rated_Result_Dao top_rated_result_dao;

        private UpdateNoteAsyncTask(Top_Rated_Result_Dao top_rated_result_dao) {
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(Top_Rated_Result... result) {
            top_rated_result_dao.update(result[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Top_Rated_Result, Void, Void> {
        private Top_Rated_Result_Dao top_rated_result_dao;

        private DeleteNoteAsyncTask(Top_Rated_Result_Dao top_rated_result_dao) {
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(Top_Rated_Result... result) {
            top_rated_result_dao.delete(result[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Top_Rated_Result_Dao top_rated_result_dao;

        private DeleteAllNotesAsyncTask(Top_Rated_Result_Dao top_rated_result_dao) {
            this.top_rated_result_dao = top_rated_result_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            top_rated_result_dao.deleteAllNotes();
            return null;
        }
    }
}