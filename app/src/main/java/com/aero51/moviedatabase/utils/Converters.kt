package com.aero51.moviedatabase.utils

import androidx.room.TypeConverter
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel.Ratings
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse.KnownFor
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse.CreatedBy
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse.Season
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

//Room cannot store lists and Date among others  so type converters must be implemented
object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun fromString(value: String?): List<Int> {
        val listType = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromArrayList(list: List<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @JvmStatic
    @TypeConverter
    fun fromString2(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @JvmStatic
    @TypeConverter
    fun toRatingsList(value: String?): List<Ratings>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Ratings?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromRatingsList(ratings: List<Ratings?>?): String {
        val gson = Gson()
        return gson.toJson(ratings)
    }

    @JvmStatic
    @TypeConverter
    fun toMovieProductionCompaniesList(value: String?): List<MovieDetailsResponse.ProductionCompany>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MovieDetailsResponse.ProductionCompany?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromMovieProductionCompaniesList(productionCompanies: List<MovieDetailsResponse.ProductionCompany?>?): String {
        val gson = Gson()
        return gson.toJson(productionCompanies)
    }

    @JvmStatic
    @TypeConverter
    fun toTvShowProductionCompaniesList(value: String?): List<TvShowDetailsResponse.ProductionCompany>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TvShowDetailsResponse.ProductionCompany?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromTvShowProductionCompaniesList(productionCompanies: List<TvShowDetailsResponse.ProductionCompany?>?): String {
        val gson = Gson()
        return gson.toJson(productionCompanies)
    }

    @JvmStatic
    @TypeConverter
    fun toGenresForMovieList(value: String?): List<MovieGenre>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MovieGenre?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromGenreForMovieList(movieGenres: List<MovieGenre?>?): String {
        val gson = Gson()
        return gson.toJson(movieGenres)
    }

    @JvmStatic
    @TypeConverter
    fun toGenresForTvShowList(value: String?): List<TvShowGenre>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TvShowGenre?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromGenreForTvShowList(tvShowGenres: List<TvShowGenre?>?): String {
        val gson = Gson()
        return gson.toJson(tvShowGenres)
    }

    @JvmStatic
    @TypeConverter
    fun toCreatedByList(value: String?): List<CreatedBy>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CreatedBy?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromCreatedByList(createdByList: List<CreatedBy?>?): String {
        val gson = Gson()
        return gson.toJson(createdByList)
    }

    @JvmStatic
    @TypeConverter
    fun toSeasonsList(value: String?): List<Season>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Season?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromSeasonsList(seasonsList: List<Season?>?): String {
        val gson = Gson()
        return gson.toJson(seasonsList)
    }

    @JvmStatic
    @TypeConverter
    fun toKnownForList(value: String?): List<KnownFor>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<KnownFor?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromKnownForList(knownForList: List<KnownFor?>?): String {
        val gson = Gson()
        return gson.toJson(knownForList)
    }

    @JvmStatic
    @TypeConverter
    fun toMoviesWithPersonCast(value: String?): List<MoviesWithPerson.Cast>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MoviesWithPerson.Cast>>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromMoviesWithPersonCast(cast: List<MoviesWithPerson.Cast>): String {
        val gson = Gson()
        return gson.toJson(cast)
    }

    @JvmStatic
    @TypeConverter
    fun toMoviesWithPersonCrew(value: String?): List<MoviesWithPerson.Crew>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MoviesWithPerson.Crew>>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromMoviesWithPersonCrew(crew: List<MoviesWithPerson.Crew>): String {
        val gson = Gson()
        return gson.toJson(crew)
    }
    @JvmStatic
    @TypeConverter
    fun toTvShowsWithPersonCrew(value: String?): List<TvShowWithPerson.Crew>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TvShowWithPerson.Crew>>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromTvShowsWithPersonCrew(crew: List<TvShowWithPerson.Crew>): String {
        val gson = Gson()
        return gson.toJson(crew)
    }
    @JvmStatic
    @TypeConverter
    fun toTvShowsWithPersonCast(value: String?): List<TvShowWithPerson.Cast>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TvShowWithPerson.Cast>>() {}.type
        return gson.fromJson(value, type)
    }

    @JvmStatic
    @TypeConverter
    fun fromTvShowsWithPersonCast(cast: List<TvShowWithPerson.Cast>): String {
        val gson = Gson()
        return gson.toJson(cast)
    }
}