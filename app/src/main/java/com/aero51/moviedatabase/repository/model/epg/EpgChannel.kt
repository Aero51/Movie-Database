package com.aero51.moviedatabase.repository.model.epg

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "epg_channel")
class EpgChannel : Comparable<String?> {
    @PrimaryKey(autoGenerate = true)
    var channel_db_id: Int? = null
    var name: String? = null
    var display_name: String? = null

    override fun compareTo(other: String?): Int {
        return name!!.compareTo(other!!)
    }
}