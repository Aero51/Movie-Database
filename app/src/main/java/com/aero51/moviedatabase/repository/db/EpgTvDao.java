package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;

import java.util.List;

@Dao
public interface EpgTvDao {

    @Query("SELECT * FROM epg_channel ")
    LiveData<List<EpgChannel>> getLiveDataChannels();

    @Query("SELECT * FROM epg_program ")
    LiveData<List<EpgProgram>> getLiveDataPrograms();

    @Query("SELECT * FROM epg_program")
    DataSource.Factory<Integer,EpgProgram> getAllPrograms();



    @Insert
    void insertProgramsList(List<EpgProgram> epgPrograms);

    @Insert
    void insertChannelsList(List<EpgChannel> epgChannels);

    @Query("DELETE FROM epg_program")
    void deleteAllPrograms();

    @Query("DELETE FROM epg_channel")
    void deleteAllChannels();

    @Query("DELETE  FROM epg_program WHERE channel = :channelName ")
    void deleteProgramsForChannel(String channelName);

    @Query("SELECT * FROM epg_program WHERE channel = :channelName")
    LiveData<List<EpgProgram>> getLiveDataProgramsForChannel(String channelName);

}
