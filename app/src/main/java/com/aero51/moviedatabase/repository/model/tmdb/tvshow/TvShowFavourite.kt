package com.aero51.moviedatabase.repository.model.tmdb.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_favourite")
class TvShowFavourite {

    @PrimaryKey(autoGenerate = true)
    var db_id = 0
    var genres: List<TvShowGenresResponse.TvShowGenre>? = null
    var id: Int? = null
    var overview: String? = null
    var popularity: Double? = null
    var production_companies: List<TvShowDetailsResponse.ProductionCompany>? = null
    var tagline: String? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
    var backdrop_path: String? = null
    var poster_path: String? = null
    var first_air_date: String? = null
    var episode_run_time: List<Int>? = null
    //TODO var next_episode_to_air: String? = null  array is needed
    var original_name: String? = null
    var number_of_episodes: Int? = null
    var number_of_seasons: Int? = null
    var status: String? = null
    var in_production: Boolean? = null
    var created_by: List<TvShowDetailsResponse.CreatedBy>? = null
    var seasons: List<TvShowDetailsResponse.Season>? = null
}