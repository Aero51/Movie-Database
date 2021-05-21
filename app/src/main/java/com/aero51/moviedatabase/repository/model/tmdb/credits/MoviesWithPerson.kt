package com.aero51.moviedatabase.repository.model.tmdb.credits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_with_person")
data class MoviesWithPerson(
        @PrimaryKey(autoGenerate = true)
        val db_id: Int,
        val cast: List<Cast>,
        val crew: List<Crew>,
        val id: Int
) {

    data class Cast(
            var poster_path: String,
            var title: String,
            var video: Boolean,
            var vote_average: Double,
            var overview: String,
            var release_date: String,
            var adult: Boolean,
            var backdrop_path: String,
            var id: Int,
            var genre_ids: List<Int>,
            var vote_count: Int,
            var original_language: String,
            var original_title: String,
            var popularity: Double,
            var character: String,
            var credit_id: String,
            var order: Int
    ) {

    }

    data class Crew(
            var poster_path: String,
            var title: String,
            var video: Boolean,
            var vote_average: Double,
            var overview: String,
            var release_date: String,
            var adult: Boolean,
            var backdrop_path: String,
            var id: Int,
            var genre_ids: List<Int>,
            var vote_count: Int,
            var original_language: String,
            var original_title: String,
            var popularity: Double,
            var character: String,
            var credit_id: String,
            var department: String,
            var job: String
    ) {

    }
}