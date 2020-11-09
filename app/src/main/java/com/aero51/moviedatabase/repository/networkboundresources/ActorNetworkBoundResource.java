package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.CreditsDao;
import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.repository.retrofit.TheMovieDbApi;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import static com.aero51.moviedatabase.utils.Constants.TMDB_API_KEY;

public class ActorNetworkBoundResource extends NetworkBoundResource<Actor, Actor> {
    private Integer actor_id;
    private Database database;
    private CreditsDao creditsDao;

    public ActorNetworkBoundResource(AppExecutors appExecutors, Application application, Integer actor_id) {
        super(appExecutors);
        database = Database.getInstance(application);
        creditsDao = database.get_credits_dao();
        this.actor_id = actor_id;
    }

    @Override
    protected void saveCallResult(@NonNull Actor item) {
        //this is executed on background thread
        creditsDao.insertActor(item);
        Log.d("moviedatabaselog", "saveCallResult actor id: " + item.getId() );
    }


    @Override
    protected boolean shouldFetch(@Nullable Actor data) {
        Log.d("moviedatabaselog", "shouldFetch data==null: "+String.valueOf(data==null) );
        return  data==null;
    }

    @NonNull
    @Override
    protected LiveData<Actor> loadFromDb() {
        return creditsDao.getActor(actor_id);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<Actor>> createCall() {
        TheMovieDbApi theMovieDbApi = RetrofitInstance.getTmdbApiService();
        return theMovieDbApi.getLivePerson(actor_id, TMDB_API_KEY);
    }
}
