package com.aero51.moviedatabase.utils

import com.aero51.moviedatabase.BuildConfig

object Constants {
    const val TMDB_API_KEY = BuildConfig.API_KEY
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val EPG_BASE_URL = "https://springbootepd.herokuapp.com/"
    const val REGION = "us"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
    const val BACKDROP_SIZE_W300 = "w300"
    const val BACKDROP_SIZE_W780 = "w780"
    const val BACKDROP_SIZE_W1280 = "w1280"
    const val BACKDROP_SIZE_ORIGINAL = "original"
    const val POSTER_SIZE_W92 = "w92"
    const val POSTER_SIZE_W154 = "w154"
    const val POSTER_SIZE_W185 = "w185"
    const val POSTER_SIZE_W342 = "w342"
    const val POSTER_SIZE_W500 = "w500"
    const val POSTER_SIZE_W780 = "w780"
    const val POSTER_SIZE_ORIGINAL = "original"
    const val PROFILE_SIZE_ORIGINAL = "original"
    const val PROFILE_SIZE_W45 = "w45"
    const val PROFILE_SIZE_W185 = "w185"
    const val PROFILE_SIZE_H632 = "h632"
    const val CROATIAN_CHANNELS_PREFERENCE = "croatian_channels_multi_select_list"
    const val SERBIAN_CHANNELS_PREFERENCE = "serbian_channels_multi_select_list"
    const val BOSNIAN_CHANNELS_PREFERENCE = "bosnian_channels_multi_select_list"
    const val MONTENEGRO_CHANNELS_PREFERENCE = "montenegro_channels_multi_select_list"
    const val MOVIE_CHANNELS_PREFERENCE = "movie_channels_multi_select_list"
    const val SPORTS_CHANNELS_PREFERENCE = "sports_channels_multi_select_list"
    const val DOCUMENTARY_CHANNELS_PREFERENCE = "documentary_channels_multi_select_list"
    const val NEWS_CHANNELS_PREFERENCE = "news_channels_multi_select_list"
    const val MUSIC_CHANNELS_PREFERENCE = "music_channels_multi_select_list"
    const val CHILDREN_CHANNELS_PREFERENCE = "children_channels_multi_select_list"
    const val ENTERTAINMENT_CHANNELS_PREFERENCE = "entertainment_channels_multi_select_list"
    const val CROATIAN_EXPANDED_CHANNELS_PREFERENCE = "croatian_expanded_channels_multi_select_list"
    const val LOG = "moviedatabaselog"
    const val LOG2 = "moviedatabaselog2"
    const val DATABASE_NAME = "Movie.db"
    const val MOVIES_FIRST_PAGE = 1
    const val TV_SHOWS_FIRST_PAGE = 1
    const val TOP_RATED_MOVIE_TYPE_ID = 0
    const val POPULAR_MOVIE_TYPE_ID = 1
}