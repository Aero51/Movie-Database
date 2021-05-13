package com.aero51.moviedatabase.repository.model.tmdb.tvshow

class TvShow {
    private val db_id = 0
    var backdrop_path: String? = null
    var first_air_date: String? = null
    var genre_ids: List<Int>? = null
    var id: Int = 0
    var name: String = ""
    var origin_country: List<String>? = null
    var original_language: String? = null
    var original_name: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var poster_path: String? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
}