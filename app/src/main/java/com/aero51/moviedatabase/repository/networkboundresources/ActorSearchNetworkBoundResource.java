package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class ActorSearchNetworkBoundResource extends NetworkBoundResource<ActorSearchResponse, ActorSearchResponse.ActorSearch> {
    private String actor_name;
    private Database database;
    private CreditsDao creditsDao;

    public ActorSearchNetworkBoundResource(AppExecutors appExecutors, Application application, String actor_name) {
        super(appExecutors);
        database = Database.getInstance(application);
        creditsDao = database.get_credits_dao();
        this.actor_name = actor_name;
    }

    @Override
    protected void saveCallResult(@NonNull ActorSearchResponse item) {
        if(item.getResults().size()>0) {
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    creditsDao.insertActorSearch(item.getResults().get(0));
                }
            });
        }
    }

    @Override
    protected boolean shouldFetch(@Nullable ActorSearchResponse.ActorSearch data) {
        Log.d(Constants.LOG, "shouldFetch data==null: "+String.valueOf(data==null) );
        return  data==null;
    }


    @NonNull
    @Override
    protected LiveData<ActorSearchResponse.ActorSearch> loadFromDb() {
        return creditsDao.getActorSearch(actor_name);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<ActorSearchResponse>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        return theMovieDbApi.getLivePersonSearch(TMDB_API_KEY,actor_name);
    }
}
