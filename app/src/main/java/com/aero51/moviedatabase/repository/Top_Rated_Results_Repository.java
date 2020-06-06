package com.aero51.moviedatabase.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;


public class Top_Rated_Results_Repository {
    private Top_Rated_Result_Dao top_rated_result_dao;

    //creating livedata for PagedList  and PagedKeyedDataSource
    private LiveData<PagedList<Top_Rated_Result>> topRatedResultsPagedList;


    public Top_Rated_Results_Repository(Application application) {
        Top_Rated_Results_Database database = Top_Rated_Results_Database.getInstance(application);
        top_rated_result_dao = database.top_rated_results_dao();

        //getting our data source factory
        TopRatedResultDataSourceFactory topRatedResultDataSourceFactory = new TopRatedResultDataSourceFactory();
        //getting the live data source from data source factory
       DataSource<Integer, Top_Rated_Result>  mostRecentDataSource = topRatedResultDataSourceFactory.create();


        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(60)
                        .setPageSize(TopRatedResultDataSource.PAGE_SIZE).build();

        //Building the paged list
        topRatedResultsPagedList = (new LivePagedListBuilder(topRatedResultDataSourceFactory, pagedListConfig)).build();
    }

    public LiveData<PagedList<Top_Rated_Result>> getTopRatedResultsPagedList() {
        return topRatedResultsPagedList;
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