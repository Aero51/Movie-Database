package com.aero51.moviedatabase.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.utils.PrePopulatedChannels;

import java.util.ArrayList;
import java.util.List;

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

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        PrePopulatedChannels prePopulatedChannels = new PrePopulatedChannels();

        populateMultiSelectListPrefs(prePopulatedChannels.getCroatianList(),CROATIAN_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getSerbianList(),SERBIAN_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getBosnianList(),BOSNIAN_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getMontenegroList(),MONTENEGRO_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getMovieList(),MOVIE_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getSportsList(),SPORTS_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getDocumentaryList(),DOCUMENTARY_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getNewsList(),NEWS_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getMusicList(),MUSIC_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getChildrenList(),CHILDREN_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getEntertainmentList(),ENTERTAINMENT_CHANNELS_MULTI_SELECT_LIST);
        populateMultiSelectListPrefs(prePopulatedChannels.getCroatianExpandedList(),CROATIAN_EXPANDED_CHANNELS_MULTI_SELECT_LIST);

    }

private void populateMultiSelectListPrefs(List<EpgChannel> channelsList,String key){

    List<List<String>> preferenceEntriesAndValuesList=new ArrayList<>();
    preferenceEntriesAndValuesList = populatePreferenceEntriesAndValues(channelsList);
    // Cast from a Preference to a MultiSelectListPreference
    MultiSelectListPreference multi_select_list_pref = (MultiSelectListPreference) findPreference(key);
    // Set the entries position 0 is channel entries and 1  channel values
    multi_select_list_pref.setEntries(preferenceEntriesAndValuesList.get(0).toArray(new CharSequence[preferenceEntriesAndValuesList.get(0).size()]));
    multi_select_list_pref.setEntryValues(preferenceEntriesAndValuesList.get(1).toArray(new CharSequence[preferenceEntriesAndValuesList.get(1).size()]));

}

    private List<List<String>> populatePreferenceEntriesAndValues(List<EpgChannel> channelsList) {
        List<String> channelsEntries = new ArrayList<>();
        List<String> channelsValues = new ArrayList<>();
        for (int i = 0; i < channelsList.size(); i++) {
            channelsEntries.add(channelsList.get(i).getDisplay_name());
            channelsValues.add(channelsList.get(i).getName());
        }
        List<List<String>> preferenceEntriesAndValuesList = new ArrayList<>();
        preferenceEntriesAndValuesList.add(channelsEntries);
        preferenceEntriesAndValuesList.add(channelsValues);

        return preferenceEntriesAndValuesList;
    }

}