package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.EpgTvDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImage;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.retrofit.EpgApi;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

public class EpgChannelsNetworkBoundResource extends NetworkBoundResource<List<EpgChannel>, List<EpgChannel>> {
    private MoviesDatabase database;
    private EpgTvDao epgTvDao;

    public EpgChannelsNetworkBoundResource(AppExecutors appExecutors, Application application) {
        super(appExecutors);
        database = MoviesDatabase.getInstance(application);
        epgTvDao=database.get_epg_tv_dao();
    }

    @Override
    protected void saveCallResult(@NonNull List<EpgChannel> item) {
        Log.d("moviedatabaselog", "EpgTv saveCallResult channels list size: " + item.size());

    }

    @Override
    protected boolean shouldFetch(@Nullable List<EpgChannel> data) {
        return true;
    }

    @NonNull
    @Override
    protected LiveData<List<EpgChannel>> loadFromDb() {

        return epgTvDao.getLiveDataChannels();
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<List<EpgChannel>>> createCall() {
        EpgApi epgApi = RetrofitInstance.getEpgApiService();
        return epgApi.getLiveChannels();
    }
}
