package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class ActorSearchResponse(
        var page: Int,
        var results: List<ActorSearch>,
        var total_pages: Int,
        var total_results: Int? = null
) {


    @Entity(tableName = "actor_search")
    data class ActorSearch(
            @PrimaryKey(autoGenerate = true)
            var db_id: Int,
            var adult: Boolean,
            var gender: Int,
            var id: Int,
            var known_for: List<KnownFor>,
            var known_for_department: String,
            var name: String,
            var popularity: Double,
            var profile_path: String? = null
    ) {}

        data class KnownFor(
                var backdrop_path: String,
                var genre_ids: List<Int>,
                var id: Int,
                var media_type: String,
                var original_language: String,
                var original_title: String,
                var overview: String,
                var poster_path: String,
                var release_date: String,
                var title: String,
                var video: Boolean,
                var vote_average: Double,
                var vote_count: Double,
        )
        {}
    }
