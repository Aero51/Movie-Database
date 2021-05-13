package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie_credits")
class MovieCredits {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null

    @Ignore
    @SerializedName("cast")
    var movieCast: List<MovieCast>? = null

    @Ignore
    @SerializedName("crew")
    var movieCrew: List<MovieCrew>? = null

    @Entity(tableName = "movie_crew")
    class MovieCrew {
        @PrimaryKey(autoGenerate = true)
        var db_id: Int? = null
        var credit_id: String? = null
        var department: String? = null
        var gender: Int? = null
        var id: Int? = null
        var job: String? = null
        var name: String? = null
        var profile_path: String? = null
        var movie_id: Int? = null
    }

    @Entity(tableName = "movie_cast")
    class MovieCast : Serializable {
        @PrimaryKey(autoGenerate = true)
        var db_id: Int? = null
        var cast_id: Int? = null
        var character: String? = null
        var credit_id: String? = null
        var gender: Int? = null
        var id: Int? = null
        var name: String? = null
        var order: Int? = null
        var profile_path: String? = null
        var movie_id: Int? = null
    }
}