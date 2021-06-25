package com.aero51.moviedatabase.repository.model.tmdb.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre

@Entity(tableName = "tv_show_details")
class TvShowDetailsResponse {

    @PrimaryKey(autoGenerate = true)
    var db_id = 0
    var genres: List<TvShowGenre>? = null
    var id: Int? = null
    var overview: String? = null
    var popularity: Double? = null
    var production_companies: List<ProductionCompany>? = null
    var tagline: String? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
    var backdrop_path: String? = null
    var poster_path: String? = null
    var first_air_date: String? = null
     var episode_run_time: List<Int>? = null
    //TODO var next_episode_to_air: String? = null  array is needed
    var original_name: String? = null
    var name: String? = null
    var number_of_episodes: Int? = null
    var number_of_seasons: Int? = null
    var status: String? = null
    var in_production: Boolean? = null
    var created_by: List<CreatedBy>? = null
    var seasons: List<Season>? = null

    class ProductionCompany {
        var id: Int? = null
        var logo_path: String? = null
        var name: String? = null
        var origin_country: String? = null
    }

    class CreatedBy {
        var id: Int? = null
        var credit_id: String? = null
        var name: String? = null
        var gender: Int? = null
        var profile_path: String? = null
    }

    class Season {
        var air_date: String? = null
        var episode_count: Int? = null
        var id: Int? = null
        var name: String? = null
        var overview: String? = null
        var poster_path: String? = null
        var season_number: Int? = null
    }
}