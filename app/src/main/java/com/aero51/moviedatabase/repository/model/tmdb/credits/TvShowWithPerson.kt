package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows_with_person")
class TvShowWithPerson(
        @PrimaryKey(autoGenerate = true)
        val db_id: Int,
        val cast: List<Cast>,
        val crew: List<Crew>,
        val id: Int
) {
    data class Cast(
            var credit_id: String,
            var original_name: String,
            var id: Int,
            var genre_ids: List<Int>,
            var character: String,
            var name: String,
            var poster_path: String,
            var vote_count: Int,
            var vote_average: Double,
            var popularity: Double,
            var episode_count: String,
            var original_language: String,
            var first_air_date: String,
            var backdrop_path: String,
            var overview: String

    ) {

    }

    data class Crew(
            var id: Int,
            var department: String,
            var original_language: String,
            var episode_count: Int,
            var job: String,
            var overview: String,
            var original_name: String,
            var genre_ids: List<Int>,
            var name: String,
            var first_air_date: String,
            var backdrop_path: String,
            var popularity: Double,
            var vote_count: Int,
            var vote_average: Double,
            var poster_path: String,
            var credit_id: String,

            ) {
    }
}