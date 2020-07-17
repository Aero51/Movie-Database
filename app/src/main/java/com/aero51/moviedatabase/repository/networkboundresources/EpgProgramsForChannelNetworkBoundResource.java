package com.aero51.moviedatabase.repository.networkboundresources;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.EpgTvDao;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.retrofit.EpgApi;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.utils.ApiResponse;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.util.List;

public class EpgProgramsForChannelNetworkBoundResource extends NetworkBoundResource<List<EpgProgram>, List<EpgProgram>> {
    private Database database;
    private EpgTvDao epgTvDao;
    private String channelName;

    public EpgProgramsForChannelNetworkBoundResource(AppExecutors appExecutors, Application application, String channelName) {
        super(appExecutors);
        database = Database.getInstance(application);
        epgTvDao = database.get_epg_tv_dao();
        this.channelName = channelName;
    }


    @Override
    protected void saveCallResult(@NonNull List<EpgProgram> item) {
        epgTvDao.deleteProgramsForChannel(channelName);
        epgTvDao.insertProgramsList(item);
    }

    @Override
    protected boolean shouldFetch(@Nullable List<EpgProgram> data) {
        return data.size() == 0;
    }

    @NonNull
    @Override
    protected LiveData<List<EpgProgram>> loadFromDb() {
        return epgTvDao.getLiveDataProgramsForChannel(channelName);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<List<EpgProgram>>> createCall() {
        Log.d("moviedatabaselog", "EpgTv channel:" + channelName + " ,get programs createCall ");
        EpgApi epgApi = RetrofitInstance.getEpgApiService();
        return epgApi.getLiveProgramsForChannel(channelName);
    }
}
