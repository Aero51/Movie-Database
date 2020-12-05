package com.aero51.moviedatabase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.EpgTvRepository;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.AppExecutors;
import com.aero51.moviedatabase.utils.NearestTimeHelper;
import com.aero51.moviedatabase.utils.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EpgViewModel extends AndroidViewModel {
    private EpgTvRepository epgTvRepository;
    private LiveData<Resource<List<EpgProgram>>> resourceLiveData;

    private SimpleDateFormat fromUser;

    public EpgViewModel(@NonNull Application application) {
        super(application);
        AppExecutors executors = new AppExecutors();
        epgTvRepository = new EpgTvRepository(application, executors);

        fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");
    }

    public LiveData<Resource<List<EpgChannel>>> getChannels() {
        return epgTvRepository.loadChannels();
    }

    public LiveData<Resource<List<EpgProgram>>> getProgramsForChannel(String channelName) {
        resourceLiveData = epgTvRepository.loadProgramsForChannel(channelName);
        return resourceLiveData;
    }

    public LiveData<Resource<List<EpgProgram>>> getResourceLiveData() {
        return resourceLiveData;
    }


    public ChannelWithPrograms calculateTimeStuff(List<EpgProgram> programsList) {
        ChannelWithPrograms item = new ChannelWithPrograms();
        //Collections.sort(new ArrayList<String>());
        item.setProgramsList(programsList);
        int nearestTimePosition = NearestTimeHelper.getNearestTime(programsList);
        item.setNearestTimePosition(nearestTimePosition);

        String currentTimeString = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());

        Date startDate = null;
        Date stopDate = null;
        Date currentDate = null;
        try {
            startDate = fromUser.parse(programsList.get(nearestTimePosition).getStart());
            stopDate = fromUser.parse(programsList.get(nearestTimePosition).getStop());
            currentDate = fromUser.parse(currentTimeString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        double startTime = startDate.getTime();
        double stopTime = stopDate.getTime();
        double currentTime = currentDate.getTime();
        //double percentage = (currentValue - minValue) / (maxValue - minValue);
        double percentage = (((currentTime - startTime) / (stopTime - startTime)) * 100);
        //  Log.d(Constants.LOG, "startTime: " + startTime+", stopTime: "+stopTime+" , currentTime: "+currentTime);
        //  Log.d(Constants.LOG, "percentage: " + percentage);
        item.setNowPlayingPercentage((int) percentage);

        return item;
    }


}
