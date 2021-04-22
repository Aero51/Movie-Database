package com.aero51.moviedatabase.repository.retrofit

import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits
import com.aero51.moviedatabase.repository.model.tmdb.movie.*
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.*
import com.aero51.moviedatabase.utils.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    //region is optional
    @GET("discover/movie")
    fun getTopRatedMovies(@Query("api_key") api_key: String
                          , @Query("page") page: Int, @Query("region") region: String, @Query("vote_count.gte") vote_count: Int, @Query("sort_by") sort_by: String, @Query("without_genres") without_genres: Int): Call<TopRatedMoviesPage>

    //region is optional
    @GET("discover/movie")
    fun getPopularMovies(@Query("api_key") api_key: String
                         , @Query("page") page: Int, @Query("region") region: String, @Query("vote_count.gte") vote_count: Int, @Query("sort_by") sort_by: String): Call<PopularMoviesPage>

    //region is optional
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") api_key: String
                            , @Query("page") page: Int, @Query("region") region: String): Call<NowPlayingMoviesPage>

    //region is optional
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") api_key: String
                          , @Query("page") page: Int, @Query("region") region: String): Call<UpcomingMoviesPage>

    //region is optional
    @GET("tv/popular")
    fun getPopularTvShows(@Query("api_key") api_key: String
                          , @Query("page") page: Int, @Query("region") region: String): Call<PopularTvShowsPage>

    @GET("discover/tv")
    fun getPopularTvShows(@Query("api_key") api_key: String
                          , @Query("page") page: Int, @Query("watch_region") region: String, @Query("sort_by") sort_by: String, @Query("vote_count.gte") vote_count: Int): Call<PopularTvShowsPage>

    //region is optional
    @GET("tv/on_the_air")
    fun getAiringTvShows(@Query("api_key") api_key: String
                         , @Query("page") page: Int, @Query("region") region: String): Call<AiringTvShowsPage>

    @GET("trending/tv/week")
    fun getTrendingTvShows(@Query("api_key") api_key: String
                           , @Query("page") page: Int): Call<TrendingTvShowsPage>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Query("api_key") api_key: String, @Path("movie_id") movie_id: Int): Call<MovieCredits>

    @GET("movie/{movie_id}/credits")
    fun getLiveMovieCredits(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String): LiveData<ApiResponse<MovieCredits>>

    @GET("person/{person_id}")
    fun getLivePerson(@Path("person_id") person_id: Int, @Query("api_key") api_key: String): LiveData<ApiResponse<Actor>>

    // GET /person/{person_id}/images
    @GET("person/{person_id}/images")
    fun getLivePersonImages(@Path("person_id") person_id: Int, @Query("api_key") api_key: String): LiveData<ApiResponse<ActorImagesResponse>>

    @GET("search/person")
    fun getLivePersonSearch(@Query("api_key") api_key: String, @Query("query") person: String): LiveData<ApiResponse<ActorSearchResponse>>

    @GET("search/person")
    fun getPersonsSearch(@Query("api_key") api_key: String, @Query("query") person: String, @Query("page") page: Int): Call<ActorSearchResponse>

    @GET("search/movie")
    fun getMoviesSearch(@Query("api_key") api_key: String, @Query("query") movieSearch: String, @Query("page") page: Int): Call<MovieSearchResult>

    @GET("search/tv")
    fun getTvShowsSearch(@Query("api_key") api_key: String, @Query("query") movieSearch: String, @Query("page") page: Int): Call<TvShowSearchResult>

    @GET("genre/movie/list")
    fun getLiveMovieGenres(@Query("api_key") api_key: String): LiveData<ApiResponse<MovieGenresResponse>>

    @GET("genre/tv/list")
    fun getLiveTvGenres(@Query("api_key") api_key: String): LiveData<ApiResponse<TvShowGenresResponse>>

    @GET("discover/movie")
    fun getMoviesByGenre(@Query("api_key") api_key: String
                         , @Query("page") page: Int, @Query("with_genres") with_genres: Int, @Query("sort_by") sort_by: String, @Query("vote_count.gte") vote_count: Int): Call<MoviesByGenrePage>

    @GET("discover/tv")
    fun getTvShowsByGenre(@Query("api_key") api_key: String
                          , @Query("page") page: Int, @Query("with_genres") with_genres: Int, @Query("sort_by") sort_by: String, @Query("vote_count.gte") vote_count: Int, @Query("without_genres") without_genre: Int): Call<TvShowsByGenrePage>

    @GET("movie/{movie_id}/videos")
    fun getLiveVideosForMovie(@Path("movie_id")movie_id: Int,@Query("api_key") api_key: String): LiveData<ApiResponse<MovieVideosResponse>>


}