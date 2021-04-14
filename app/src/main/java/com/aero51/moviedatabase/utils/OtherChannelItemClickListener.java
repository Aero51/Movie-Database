package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;

public interface OtherChannelItemClickListener {

    void onItemClick(int position, EpgOtherChannel otherChannel);
}
