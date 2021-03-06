package com.aero51.moviedatabase.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aero51.moviedatabase.repository.model.epg.EpgChannel
import com.aero51.moviedatabase.repository.model.epg.EpgProgram

@Dao
interface EpgTvDao {
    @get:Query("SELECT * FROM epg_channel ")
    val liveDataChannels: LiveData<List<EpgChannel?>?>?

    @get:Query("SELECT * FROM epg_program ")
    val liveDataPrograms: LiveData<List<EpgProgram?>?>?

    @get:Query("SELECT * FROM epg_program")
    val allPrograms: DataSource.Factory<Int?, EpgProgram?>?

    @Insert
    fun insertProgramsList(epgPrograms: List<EpgProgram?>?)

    @Insert
    fun insertChannelsList(epgChannels: List<EpgChannel?>?)

    @Query("DELETE FROM epg_program")
    fun deleteAllPrograms()

    @Query("DELETE FROM epg_channel")
    fun deleteAllChannels()

    @Query("DELETE  FROM epg_program WHERE channel = :channelName ")
    fun deleteProgramsForChannel(channelName: String?)

    @Query("SELECT * FROM epg_program WHERE channel = :channelName")
    fun getLiveDataProgramsForChannel(channelName: String?): LiveData<List<EpgProgram>>
}