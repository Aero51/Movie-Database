package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImage;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class ActorImagesNetworkBoundResource extends NetworkBoundResource<ActorImagesResponse, List<ActorImage>> {
    private Integer actor_id;
    private Database database;
    private CreditsDao creditsDao;

    public ActorImagesNetworkBoundResource(AppExecutors appExecutors, Application application, Integer actor_id) {
        super(appExecutors);
        database = Database.getInstance(application);
        creditsDao = database.get_credits_dao();
        this.actor_id = actor_id;
    }

    @Override
    protected void saveCallResult(@NonNull ActorImagesResponse response) {
        //this is executed on background thread
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                creditsDao.insertActorImagesResponse(response);
            }
        });
        Log.d(Constants.LOG, "saveCallResult actor id: " + response.getId() + " ,images list  size: " + response.getImages().size());
    }

    @Override
    protected boolean shouldFetch(@Nullable List<ActorImage> data) {
        return data.size() == 0;
    }

    @NonNull
    @Override
    protected LiveData<List<ActorImage>> loadFromDb() {
        return creditsDao.getActorImages(actor_id);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<ActorImagesResponse>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        return theMovieDbApi.getLivePersonImages(actor_id, TMDB_API_KEY);
    }
}
