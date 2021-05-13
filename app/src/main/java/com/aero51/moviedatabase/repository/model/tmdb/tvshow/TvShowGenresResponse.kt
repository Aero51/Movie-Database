package com.aero51.moviedatabase.repository.model.tmdb.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

class TvShowGenresResponse(var genres: List<TvShowGenre>) {

    @Entity(tableName = "tv_show_genre")
    class TvShowGenre {
        @PrimaryKey(autoGenerate = true)
        var db_id = 0
        var id: Int? = null
        var name: String? = null
    }
}