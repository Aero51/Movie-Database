package com.aero51.moviedatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TopRatedResultViewModel extends AndroidViewModel {
    private Top_Rated_Results_Repository repository;
    private LiveData<List<Top_Rated_Result>> allResults;
    private LiveData<List<Top_Rated_Movies_Page>> allMovies;

    public TopRatedResultViewModel(@NonNull Application application) {
        super(application);
        repository = new Top_Rated_Results_Repository(application);
        allResults = repository.getAllResults();

    }

  /*  public NoteViewModel() {
        //super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }
    */


    public void insert(Top_Rated_Result result) {
        repository.insert(result);
    }

    public void update(Top_Rated_Result result) {
        repository.update(result);
    }

    public void delete(Top_Rated_Result result) {
        repository.delete(result);
    }

    public void deleteAllResults() {
        repository.deleteAllResults();
    }

    public LiveData<List<Top_Rated_Result>> getAllResults(){
        return  allResults;
    }
    public LiveData<List<Top_Rated_Movies_Page>> getAllTopRatedMovies(){
        return  allMovies;
    }


    public void fetchNewPage(int pageNumber){repository.getTopRatedMovies(pageNumber);}

}
