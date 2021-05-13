package com.aero51.moviedatabase.utils;

import androidx.room.TypeConverter;

import com.aero51.moviedatabase.repository.model.omdb.OmdbModel;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;


//Room cannot store lists and Date among others  so type converters must be implemented
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static List<Integer> fromString(String value) {
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<String> fromString2(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static List<OmdbModel.Ratings> toRatingsList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OmdbModel.Ratings>>() {
        }.getType();
        List<OmdbModel.Ratings> ratingsList = gson.fromJson(value, type);
        return ratingsList;
    }

    @TypeConverter
    public static String fromRatingsList(List<OmdbModel.Ratings> ratings) {
        Gson gson = new Gson();
        String json = gson.toJson(ratings);
        return json;
    }

    @TypeConverter
    public static List<MovieDetailsResponse.ProductionCompany> toMovieProductionCompaniesList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieDetailsResponse.ProductionCompany>>() {
        }.getType();
        List<MovieDetailsResponse.ProductionCompany> productionCompanies = gson.fromJson(value, type);
        return productionCompanies;
    }

    @TypeConverter
    public static String fromMovieProductionCompaniesList(List<MovieDetailsResponse.ProductionCompany> productionCompanies) {
        Gson gson = new Gson();
        String json = gson.toJson(productionCompanies);
        return json;
    }

    @TypeConverter
    public static List<TvShowDetailsResponse.ProductionCompany> toTvShowProductionCompaniesList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TvShowDetailsResponse.ProductionCompany>>() {
        }.getType();
        List<TvShowDetailsResponse.ProductionCompany> productionCompanies = gson.fromJson(value, type);
        return productionCompanies;
    }

    @TypeConverter
    public static String fromTvShowProductionCompaniesList(List<TvShowDetailsResponse.ProductionCompany> productionCompanies) {
        Gson gson = new Gson();
        String json = gson.toJson(productionCompanies);
        return json;
    }

    @TypeConverter
    public static List<MovieGenresResponse.MovieGenre> toGenresForMovieList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieGenresResponse.MovieGenre>>() {
        }.getType();
        List<MovieGenresResponse.MovieGenre> movieGenres = gson.fromJson(value, type);
        return movieGenres;
    }

    @TypeConverter
    public static String fromGenreForMovieList(List<MovieGenresResponse.MovieGenre> movieGenres) {
        Gson gson = new Gson();
        String json = gson.toJson(movieGenres);
        return json;
    }

    @TypeConverter
    public static List<TvShowGenresResponse.TvShowGenre> toGenresForTvShowList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TvShowGenresResponse.TvShowGenre>>() {
        }.getType();
        List<TvShowGenresResponse.TvShowGenre> tvShowGenres = gson.fromJson(value, type);
        return tvShowGenres;
    }

    @TypeConverter
    public static String fromGenreForTvShowList(List<TvShowGenresResponse.TvShowGenre> tvShowGenres) {
        Gson gson = new Gson();
        String json = gson.toJson(tvShowGenres);
        return json;
    }

    @TypeConverter
    public static List<TvShowDetailsResponse.CreatedBy> toCreatedByList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TvShowDetailsResponse.CreatedBy>>() {
        }.getType();
        List<TvShowDetailsResponse.CreatedBy> createdByList = gson.fromJson(value, type);
        return createdByList;
    }

    @TypeConverter
    public static String fromCreatedByList(List<TvShowDetailsResponse.CreatedBy> createdByList) {
        Gson gson = new Gson();
        String json = gson.toJson(createdByList);
        return json;
    }
    @TypeConverter
    public static List<TvShowDetailsResponse.Season> toSeasonsList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TvShowDetailsResponse.Season>>() {
        }.getType();
        List<TvShowDetailsResponse.Season> seasonsList = gson.fromJson(value, type);
        return seasonsList;
    }

    @TypeConverter
    public static String fromSeasonsList(List<TvShowDetailsResponse.Season> seasonsList) {
        Gson gson = new Gson();
        String json = gson.toJson(seasonsList);
        return json;
    }

    @TypeConverter
    public static List<ActorSearchResponse.KnownFor> toKnownForList(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ActorSearchResponse.KnownFor>>() {
        }.getType();
        List<ActorSearchResponse.KnownFor> knownForList = gson.fromJson(value, type);
        return knownForList;
    }

    @TypeConverter
    public static String fromKnownForList(List<ActorSearchResponse.KnownFor> knownForList) {
        Gson gson = new Gson();
        String json = gson.toJson(knownForList);
        return json;
    }
}