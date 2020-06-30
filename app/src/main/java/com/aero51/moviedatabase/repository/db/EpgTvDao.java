package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;

import java.util.List;

@Dao
public interface EpgTvDao {

    @Query("SELECT * FROM epg_channel ")
    LiveData<List<EpgChannel>> getLiveDataChannels();

    @Query("SELECT * FROM epg_program ")
    LiveData<List<EpgProgram>> getLiveDataPrograms();
}
