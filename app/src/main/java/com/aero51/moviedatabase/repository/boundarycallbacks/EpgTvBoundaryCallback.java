package com.aero51.moviedatabase.repository.boundarycallbacks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.aero51.moviedatabase.repository.db.Database;
import com.aero51.moviedatabase.repository.db.EpgTvDao;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.retrofit.EpgApi;
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance;
import com.aero51.moviedatabase.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EpgTvBoundaryCallback extends PagedList.BoundaryCallback<List<EpgProgram>> {
    private AppExecutors executors;
    private Database database;
    private EpgTvDao dao;
    private MutableLiveData<NetworkState> networkState;
    private List<EpgChannel> channelList;
    private int counter=0;

    public EpgTvBoundaryCallback(Application application, AppExecutors executors, List<EpgChannel> channelList) {
        this.executors = executors;
        database = Database.getInstance(application);
        dao = database.get_epg_tv_dao();
        networkState = new MutableLiveData<>();
        this.channelList = channelList;
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.d("moviedatabaselog", "EpgTvBoundaryCallback onzeroitemsloaded, "+channelList.get(counter).getName());
        fetchProgramsForChannel(channelList.get(counter).getName());
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull List<EpgProgram> itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        Log.d("moviedatabaselog", "EpgTvBoundaryCallback onItemAtFrontLoaded");
    }

    @Override
    public void onItemAtEndLoaded(@NonNull List<EpgProgram> itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Log.d("moviedatabaselog", "EpgTvBoundaryCallback onItemAtEndLoaded");
        fetchProgramsForChannel(channelList.get(counter).getName());
    }





    public void fetchProgramsForChannel(String channelName) {
        networkState.postValue(NetworkState.LOADING);
        EpgApi epgApi = RetrofitInstance.getEpgApiService();
        Call<List<EpgProgram>> call = epgApi.getProgramsForChannel(channelName);
        call.enqueue(new Callback<List<EpgProgram>>() {
            @Override
            public void onResponse(Call<List<EpgProgram>> call, Response<List<EpgProgram>> response) {
                if (!response.isSuccessful()) {
                    Log.d("moviedatabaselog", "fetchProgramsForChannel Response unsuccesful: " + response.code() + " ,channel name: " + channelName);
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    return;
                }
                Log.d("moviedatabaselog", "fetchProgramsForChannel Response ok: " + response.code() + " ,channel name: " + channelName);
                List<EpgProgram> list = response.body();
                counter= counter+1;
                insertListToDb(list);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<List<EpgProgram>> call, Throwable t) {
                Log.d("moviedatabaselog", "fetchProgramsForChannel onFailure: " + t.getMessage() + " ,channel name: " + channelName);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
            }
        });
    }

    private void insertListToDb(List<EpgProgram> list) {
        Runnable runnable = () -> {
            dao.insertProgramsList(list);
        };
        Runnable diskRunnable = () -> database.runInTransaction(runnable);
        executors.diskIO().execute(diskRunnable);
    }
}
