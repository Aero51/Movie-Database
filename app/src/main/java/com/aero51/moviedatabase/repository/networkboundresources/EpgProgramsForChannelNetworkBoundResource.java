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
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.NetworkBoundResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        boolean noData = data.size() == 0;
        boolean shouldFetch = false;
        boolean isGreater = false;
        if (!noData) {
            Date currentTime = null;
            Date programStartTime = null;
            try {
                currentTime = Calendar.getInstance().getTime();
               // Log.d(Constants.LOG2, "currentTime! "+currentTime.getTime());


                programStartTime = new SimpleDateFormat("yyyyMMddHHmmSS").parse(data.get(data.size() - 1).getStart());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //adding 10 hours == 36000000 ms
            if (programStartTime.getTime() > currentTime.getTime()+36000000) {
                isGreater = true;
            }
        }

        if ((noData == true) || (isGreater == false)) {
            shouldFetch = true;
        }
        //Log.d(Constants.LOG,data.get(data.size()-1).getStart());
        return shouldFetch;
    }

    @NonNull
    @Override
    protected LiveData<List<EpgProgram>> loadFromDb() {
        return epgTvDao.getLiveDataProgramsForChannel(channelName);
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<List<EpgProgram>>> createCall() {
        Log.d(Constants.LOG, "EpgTv channel:" + channelName + " ,get programs createCall ");
        EpgApi epgApi = RetrofitInstance.getEpgApiService();
        return epgApi.getLiveProgramsForChannel(channelName);
    }
}
