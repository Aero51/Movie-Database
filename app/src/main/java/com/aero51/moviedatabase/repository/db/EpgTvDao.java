package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
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

    @Insert
    void insertProgramsList(List<EpgProgram> epgPrograms);

    @Query("DELETE FROM epg_program")
    void deleteAllPrograms();
}
