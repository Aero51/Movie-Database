package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tv_show_credits")
class TvShowCredits {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null

    @Ignore
    @SerializedName("cast")
    var tvShowCast: List<TvShowCast>? = null

    @Ignore
    @SerializedName("crew")
    var tvShowCrew: List<TvShowCrew>? = null

    @Entity(tableName = "tv_show_crew")
    class TvShowCrew {
        @PrimaryKey(autoGenerate = true)
        var db_id: Int? = null
        var credit_id: String? = null
        var department: String? = null
        var gender: Int? = null
        var id: Int? = null
        var job: String? = null
        var name: String? = null
        var profile_path: String? = null
        var tv_show_id: Int? = null
    }

    @Entity(tableName = "tv_show_cast")
    class TvShowCast : Serializable {
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
        var tv_show_id: Int? = null
    }
}