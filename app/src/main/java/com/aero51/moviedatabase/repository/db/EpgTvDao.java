package com.aero51.moviedatabase.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;

import java.util.List;

@Dao
public interface EpgTvDao {

    @Query("SELECT * FROM epg_channel ")
    LiveData<List<EpgChannel>> getLiveDataChannels();
}
