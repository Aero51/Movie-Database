package com.aero51.moviedatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Rated_Results_Repository {
    private Top_Rated_Result_Dao top_rated_result_dao;
    private MutableLiveData<List<Top_Rated_Result>> allResults;
    private List<Top_Rated_Result> top_rated_result_list;


    public static final String API_KEY = "8ba72532be79fd82366e924e791e0c71";
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;

    private TheMovieDbApi theMovieDbApi;

    public Top_Rated_Results_Repository(Application application) {
        Top_Rated_Results_Database database = Top_Rated_Results_Database.getInstance(application);
        top_rated_result_dao = database.top_rated_results_dao();

        allResults = new MutableLiveData<>();
        //allResults=  top_rated_result_dao.getAllResults();

        top_rated_result_list = new ArrayList<>();

        theMovieDbApi = RetrofitInstance.getApiService();
        getTopRatedMovies(TOP_RATED_MOVIES_FIRST_PAGE);


    }

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


    public void insert(Top_Rated_Result top_rated_result) {
        new InsertNoteAsyncTask(top_rated_result_dao).execute(top_rated_result);
    }

    public void update(Top_Rated_Result top_rated_result) {
        new UpdateNoteAsyncTask(top_rated_result_dao).execute(top_rated_result);
    }

    public void delete(Top_Rated_Result top_rated_result) {
        new DeleteNoteAsyncTask(top_rated_result_dao).execute(top_rated_result);
    }

    public void deleteAllResults() {
        new DeleteAllNotesAsyncTask(top_rated_result_dao).execute();
    }

    public LiveData<List<Top_Rated_Result>> getAllResults() {
        return allResults;
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