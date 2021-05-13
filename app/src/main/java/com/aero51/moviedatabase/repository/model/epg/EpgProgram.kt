package com.aero51.moviedatabase.repository.model.epg

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "epg_program")
data class EpgProgram(
        @PrimaryKey(autoGenerate = true)
        var db_id: Int,
        var title: String,
        var subTitle: String? =null,
        var desc: String,
        var credits: String? =null,
        var date: String? =null,
        var category: String,
        var icon: String,
        var channel: String,
        var channel_display_name: String,
        var start: String,
        var stop: String,
        var programDuration: Int? = null
) : Serializable {

}