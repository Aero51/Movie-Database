package com.aero51.moviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.CreditsRepository;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;

import java.util.List;

public class TmdbDetailsViewModel extends AndroidViewModel {
    private CreditsRepository creditsRepository;

    public TmdbDetailsViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        creditsRepository = new CreditsRepository(application, executors);
    }

    public LiveData<Resource<List<MovieCredits.Cast>>> getMovieCast(Integer movie_id) {
        return creditsRepository.loadCastById(movie_id);
    }


    public LiveData<Resource<Actor>> getActorDetails(Integer actor_id) {
        Log.d(Constants.LOG, "MovieDetailsViewModel getActorDetails id: " + actor_id);
        return creditsRepository.loadActorById(actor_id);
    }

    public LiveData<Resource<List<ActorImagesResponse.ActorImage>>> getActorImages(Integer actor_id) {
        Log.d(Constants.LOG, "MovieDetailsViewModel getActorImages actor id: " + actor_id);
        return creditsRepository.loadActorImagesByActorId(actor_id);
    }

}