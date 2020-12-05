package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.BuildConfig;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String TMDB_API_KEY = BuildConfig.API_KEY;
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String EPG_BASE_URL = "https://springbootepd.herokuapp.com/";
    public static final String REGION = "us";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String BACKDROP_SIZE_W300 = "w300";
    public static final String BACKDROP_SIZE_W780 = "w780";
    public static final String BACKDROP_SIZE_W1280 = "w1280";
    public static final String BACKDROP_SIZE_ORIGINAL = "original";
    public static final String POSTER_SIZE_W92 = "w92";
    public static final String POSTER_SIZE_W154 = "w154";
    public static final String POSTER_SIZE_W185 = "w185";
    public static final String POSTER_SIZE_W342 = "w342";
    public static final String POSTER_SIZE_W500 = "w500";
    public static final String POSTER_SIZE_W780 = "w780";
    public static final String POSTER_SIZE_ORIGINAL = "original";
    public static final String PROFILE_SIZE_ORIGINAL = "original";
    public static final String PROFILE_SIZE_W45 = "w45";
    public static final String PROFILE_SIZE_W185 = "w185";
    public static final String PROFILE_SIZE_H632 = "h632";

    public static final String CROATIAN_CHANNELS_MULTI_SELECT_LIST = "croatian_channels_multi_select_list";
    public static final String SERBIAN_CHANNELS_MULTI_SELECT_LIST = "serbian_channels_multi_select_list";
    public static final String BOSNIAN_CHANNELS_MULTI_SELECT_LIST = "bosnian_channels_multi_select_list";
    public static final String MONTENEGRO_CHANNELS_MULTI_SELECT_LIST = "montenegro_channels_multi_select_list";
    public static final String MOVIE_CHANNELS_MULTI_SELECT_LIST = "movie_channels_multi_select_list";
    public static final String SPORTS_CHANNELS_MULTI_SELECT_LIST = "sports_channels_multi_select_list";
    public static final String DOCUMENTARY_CHANNELS_MULTI_SELECT_LIST = "documentary_channels_multi_select_list";
    public static final String NEWS_CHANNELS_MULTI_SELECT_LIST = "news_channels_multi_select_list";
    public static final String MUSIC_CHANNELS_MULTI_SELECT_LIST = "music_channels_multi_select_list";
    public static final String CHILDREN_CHANNELS_MULTI_SELECT_LIST = "children_channels_multi_select_list";
    public static final String ENTERTAINMENT_CHANNELS_MULTI_SELECT_LIST = "entertainment_channels_multi_select_list";
    public static final String CROATIAN_EXPANDED_CHANNELS_MULTI_SELECT_LIST = "croatian_expanded_channels_multi_select_list";

    public static final String LOG = "moviedatabaselog";
    public static final String LOG2 = "moviedatabaselog2";
    public static final String DATABASE_NAME = "Movie.db";
    public static final int MOVIES_FIRST_PAGE = 1;
    public static final int TOP_RATED_MOVIES_FIRST_PAGE = 1;
    public static final int TOP_RATED_MOVIE_TYPE_ID = 0;
    public static final int POPULAR_MOVIE_TYPE_ID = 1;






}
