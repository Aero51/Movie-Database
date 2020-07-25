package com.aero51.moviedatabase.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgTvAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AsyncTaskEpgTvChildLoader extends AsyncTask<Void, Void, Integer> {
    private EpgTvAdapter.EpgTvViewHolder holder;
    private String currentTime;
    private List<EpgProgram> list;

    public AsyncTaskEpgTvChildLoader(EpgTvAdapter.EpgTvViewHolder holder,List<EpgProgram> list) {
        this.holder = holder;
        this.list=list;
        currentTime = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());
    }


    @Override
    protected Integer doInBackground(Void... voids) {
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());
        int nearestTimePosition = NearestTimeHelper.getNearestTime(list) - 1;
        return nearestTimePosition;
    }

    @Override
    protected void onPostExecute(Integer nearestTimePosition) {
        super.onPostExecute(nearestTimePosition);

        holder.epgTvChildAdapter.setProgress(currentTime,nearestTimePosition);
        holder.epgTvChildAdapter.setList(list);
        holder.child_recycler.scrollToPosition(nearestTimePosition);

    }
}
