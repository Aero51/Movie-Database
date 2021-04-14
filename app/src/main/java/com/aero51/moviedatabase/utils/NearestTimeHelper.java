package com.aero51.moviedatabase.utils;

import android.util.Log;

import com.aero51.moviedatabase.repository.model.epg.EpgProgram;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NearestTimeHelper {


    //used to calculate position of program which start time is closest to now
    public static Integer getNearestTime(List<EpgProgram> list) {
        List<Date> dates = new ArrayList<>();
        for (EpgProgram program : list) {
            try {
                Date programStartTime = new SimpleDateFormat("yyyyMMddHHmmSS").parse(program.getStart());
                dates.add(programStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());
        Date currentDate = null;
        try {
            currentDate = new SimpleDateFormat("yyyyMMddHHmmSS").parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date nearestDate = null;
        int index = 0;
        long prevDiff = -1;
        long targetTS = currentDate.getTime();
        for (int i = 0; i < dates.size(); i++) {
            Date date = dates.get(i);
            //long currDiff = Math.abs(date.getTime() - targetTS);
            long operation=targetTS-date.getTime();
            if(operation>0)
            {
                long currDiff=0;
                currDiff=operation;
                if (prevDiff == -1 || currDiff < prevDiff) {
                    prevDiff = currDiff;
                    nearestDate = date;
                    index = i;
                }
            }
        }
        //Log.d(Constants.LOG, "index: " + index );
        return index;
    }

}
