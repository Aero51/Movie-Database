package com.aero51.moviedatabase.repository.model.tmdb.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

class TvShowVideoResponse {
    var id: Int = 0
    var results: List<TvShowVideo>? = null

    @Entity(tableName = "tv_show_video")
    class TvShowVideo {
        @PrimaryKey(autoGenerate = true)
        var db_id = 0
        var tv_show_id = 0
        var id: String? = null
        var key: String? = null
        var name: String? = null
        var site: String? = null
        var type: String? = null
        var size: Int? = null
    }
}