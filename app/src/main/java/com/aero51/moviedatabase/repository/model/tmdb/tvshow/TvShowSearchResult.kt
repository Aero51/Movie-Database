package com.aero51.moviedatabase.repository.model.tmdb.tvshow

class TvShowSearchResult {
    var page: Int = 0
    var total_results: Int? = null
    var total_pages: Int? = null
    var results: List<TvShow>? = null

    class TvShow {
        var backdrop_path: String? = null
        var first_air_date: String? = null
        var genre_ids: List<Int>? = null
        var id: Int? = null
        var name: String? = null
        var origin_country: List<String>? = null
        var original_language: String? = null
        var original_name: String? = null
        var overview: String? = null
        var popularity: Double? = null
        var poster_path: String? = null
        var vote_average: Double? = null
        var vote_count: Int? = null
    }
}