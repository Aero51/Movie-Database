package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.EpgTvDao;
import com.aero51.moviedatabase.repository.db.MoviesDatabase;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.retrofit.EpgApi;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

public class EpgProgramsForCroChannelsNetworkBoundResource extends NetworkBoundResource<List<EpgProgram>, List<EpgProgram>> {
    private MoviesDatabase database;
    private EpgTvDao epgTvDao;

    public EpgProgramsForCroChannelsNetworkBoundResource(AppExecutors appExecutors, Application application) {
        super(appExecutors);
        database = MoviesDatabase.getInstance(application);
        epgTvDao=database.get_epg_tv_dao();
    }

    @Override
    protected void saveCallResult(@NonNull List<EpgProgram> item) {
        Log.d("moviedatabaselog", "EpgTv cro programs saveCallResult channels list size: " + item.size());
    }

    @Override
    protected boolean shouldFetch(@Nullable List<EpgProgram> data) {
        return true;
    }

    @NonNull
    @Override
    protected LiveData<List<EpgProgram>> loadFromDb() {
        return epgTvDao.getLiveDataPrograms();
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<List<EpgProgram>>> createCall() {
        Log.d("moviedatabaselog", "EpgTv cro programs createCall " );
        EpgApi epgApi = RetrofitInstance.getEpgApiService();
        return epgApi.getLiveCroPrograms();
    }
}
