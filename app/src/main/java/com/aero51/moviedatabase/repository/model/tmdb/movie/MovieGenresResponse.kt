package com.aero51.moviedatabase.repository.model.tmdb.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

class MovieGenresResponse(var genres: List<MovieGenre>) {

    @Entity(tableName = "movie_genre")
    class MovieGenre(var id: Int, var name: String) {
        @PrimaryKey(autoGenerate = true)
        var db_id = 0

    }
}