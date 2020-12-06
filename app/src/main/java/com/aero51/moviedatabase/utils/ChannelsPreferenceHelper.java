package com.aero51.moviedatabase.utils;

import android.content.SharedPreferences;

import android.util.Log;

import androidx.preference.PreferenceManager;

import com.aero51.moviedatabase.MyApplication;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.aero51.moviedatabase.utils.Constants.BOSNIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CHILDREN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_EXPANDED_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.DOCUMENTARY_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.ENTERTAINMENT_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.MONTENEGRO_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.MOVIE_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.MUSIC_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.NEWS_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.SERBIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.SPORTS_CHANNELS_PREFERENCE;

public class ChannelsPreferenceHelper {


    public static List<EpgChannel> extractChannels() {


        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());

        PrePopulatedChannels prePopulatedChannels = new PrePopulatedChannels();
        List<EpgChannel> completeChannelList = new ArrayList<>();

        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getCroatianList(), CROATIAN_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMontenegroList(), MONTENEGRO_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMovieList(), MOVIE_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getSportsList(), SPORTS_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getDocumentaryList(), DOCUMENTARY_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getNewsList(), NEWS_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMusicList(), MUSIC_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getChildrenList(), CHILDREN_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getEntertainmentList(), ENTERTAINMENT_CHANNELS_PREFERENCE));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getCroatianExpandedList(), CROATIAN_EXPANDED_CHANNELS_PREFERENCE));

        Log.d(Constants.LOG2, "FINAL LIST SIZE: "+completeChannelList.size());

        return completeChannelList;
    }


    private static List<EpgChannel> processChannelGroup(SharedPreferences prefs, List<EpgChannel> channelGroupList, String prefKey) {

        Set<String> set = new HashSet<>();
        set = prefs.getStringSet(prefKey, new HashSet<String>());
        List<String> unsortedSelectionList = new ArrayList<String>(set);

        List<EpgChannel> processedChannelsList = new ArrayList<>();
        for (int i = 0; i < channelGroupList.size(); i++) {
            for (int j = 0; j < unsortedSelectionList.size(); j++) {
                if (channelGroupList.get(i).getName().equals(unsortedSelectionList.get(j))) {
                    //Log.d(Constants.LOG2, i + " , " + unsortedSelectionList.get(j));
                    processedChannelsList.add(channelGroupList.get(i));
                }

            }

        }
        //List<String> sortedPreferenceList = (List<String>) TwoListSort.sortList(unsortedList, croatianChannelNamesList);
        return processedChannelsList;
    }


    //populating channel preferences on first app run to avoid empty channels list
    public static void firstRunPreferencePopulater(List<EpgChannel> channelGroupList,String key) {

        List<String> channelNameGroupList= new ArrayList<>();
        for(int i=0;i<channelGroupList.size();i++){
            channelNameGroupList.add(channelGroupList.get(i).getName());
        }
        Set<String> preferenceSet = new HashSet<String>(channelNameGroupList);
        savePreference(key,preferenceSet);
    }
    private static void savePreference(String key, Set<String> value){
        SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();

    }
}

