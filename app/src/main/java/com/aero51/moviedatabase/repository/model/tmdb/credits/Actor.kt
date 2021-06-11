package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actor")
class Actor (
    var birthday: String?,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var biography: String,
    var place_of_birth: String?,
    var profile_path: String?,
    var imdb_id: String?,
    var homepage: String?
){}