package com.aero51.moviedatabase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.aero51.moviedatabase.utils.Constants.BOSNIAN_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.CHILDREN_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_EXPANDED_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.DOCUMENTARY_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.ENTERTAINMENT_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.MONTENEGRO_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.MOVIE_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.MUSIC_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.NEWS_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.SERBIAN_CHANNELS_MULTI_SELECT_LIST;
import static com.aero51.moviedatabase.utils.Constants.SPORTS_CHANNELS_MULTI_SELECT_LIST;

public class ChannelsPreferenceExtractor {


    public static List<EpgChannel> extractChannels(Context context) {


        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        PrePopulatedChannels prePopulatedChannels = new PrePopulatedChannels();
        List<EpgChannel> completeChannelList = new ArrayList<>();

        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getCroatianList(), CROATIAN_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMontenegroList(), MONTENEGRO_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMovieList(), MOVIE_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getSportsList(), SPORTS_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getDocumentaryList(), DOCUMENTARY_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getNewsList(), NEWS_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getMusicList(), MUSIC_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getChildrenList(), CHILDREN_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getEntertainmentList(), ENTERTAINMENT_CHANNELS_MULTI_SELECT_LIST));
        completeChannelList.addAll(processChannelGroup(prefs, prePopulatedChannels.getCroatianExpandedList(), CROATIAN_EXPANDED_CHANNELS_MULTI_SELECT_LIST));

        Log.d("sharedprefs", "FINAL LIST SIZE: "+completeChannelList.size());

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
                    //Log.d("sharedprefs", i + " , " + unsortedSelectionList.get(j));
                    processedChannelsList.add(channelGroupList.get(i));
                }

            }

        }
        //List<String> sortedPreferenceList = (List<String>) TwoListSort.sortList(unsortedList, croatianChannelNamesList);
        return processedChannelsList;
    }



}

