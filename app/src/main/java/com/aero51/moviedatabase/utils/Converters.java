package com.aero51.moviedatabase.utils;

import androidx.room.TypeConverter;

import com.aero51.moviedatabase.repository.model.omdb.OmdbModel;
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
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Integer>  list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<String> fromString2(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
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
        Type type = new TypeToken<List<OmdbModel.Ratings>>() {}.getType();
        List<OmdbModel.Ratings> ratingsList = gson.fromJson(value, type);
        return ratingsList;
    }

    @TypeConverter
    public static String fromRatingsList(List<OmdbModel.Ratings>  ratings) {
        Gson gson = new Gson();
        String json = gson.toJson(ratings);
        return json;
    }



}