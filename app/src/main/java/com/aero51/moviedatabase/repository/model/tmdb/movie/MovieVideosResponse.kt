package com.aero51.moviedatabase.repository.model.tmdb.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

class MovieVideosResponse {
    var id: Int? = null
    var results: List<MovieVideo>? = null

    @Entity(tableName = "movie_video")
    class MovieVideo {
        @PrimaryKey(autoGenerate = true)
        var db_id = 0
        var movie_id = 0
        var id: String? = null
        var key: String? = null
        var name: String? = null
        var site: String? = null
        var type: String? = null
        var size: Int? = null
    }
}