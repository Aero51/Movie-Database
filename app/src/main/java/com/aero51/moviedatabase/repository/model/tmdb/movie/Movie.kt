package com.aero51.moviedatabase.repository.model.tmdb.movie

class Movie {
    var db_id: Int = 0
    var popularity: Double? = null
    var vote_count: Int? = null
    var isVideo = false
    var poster_path: String? = null
    var id: Int = 0
    var isAdult = false
    var backdrop_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var genre_ids: List<Int>? = null
    var title: String = ""
    var vote_average: Double? = null
    var overview: String? = null
    var release_date: String? = null
}