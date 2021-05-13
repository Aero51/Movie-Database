package com.aero51.moviedatabase.repository.model.tmdb.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre

@Entity(tableName = "movie_favourite")
class MovieFavourite {
    @PrimaryKey(autoGenerate = true)
    var db_id = 0
    var budget: Int? = null
    var genres: List<MovieGenre>? = null
    var id: Int = 0
    var imdb_id: String? = null
    var original_title: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var production_companies: List<MovieDetailsResponse.ProductionCompany>? = null
    var release_date: String? = null
    var revenue: Int? = null
    var runtime: Int? = null
    var tagline: String? = null
    var title: String? = null
    var vote_average: Double? = null
    var vote_count: Int? = null
    var backdrop_path: String? = null
    var poster_path: String? = null
}