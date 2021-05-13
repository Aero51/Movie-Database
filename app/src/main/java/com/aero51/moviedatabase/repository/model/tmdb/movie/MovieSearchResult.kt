package com.aero51.moviedatabase.repository.model.tmdb.movie

import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie

class MovieSearchResult {
    var page: Int? = null
    var total_results: Int? = null
    var total_pages: Int? = null
    var results: List<NowPlayingMovie>? = null
}