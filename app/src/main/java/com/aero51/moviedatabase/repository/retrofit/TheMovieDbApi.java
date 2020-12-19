package com.aero51.moviedatabase.repository.retrofit;

import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;

import com.aero51.moviedatabase.utils.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    //region is optional
    @GET("movie/top_rated")
    Call<TopRatedMoviesPage> getTopRatedMovies(@Query("api_key") String api_key
            , @Query("page") Integer page, @Query("region") String region);

    //region is optional
    @GET("movie/popular")
    Call<PopularMoviesPage> getPopularMovies(@Query("api_key") String api_key
            , @Query("page") Integer page, @Query("region") String region);

    @GET("movie/{movie_id}/credits")
    Call<MovieCredits> getMovieCredits(@Query("api_key") String api_key, @Path("movie_id") Integer movie_id);

    @GET("movie/{movie_id}/credits")
    LiveData<ApiResponse<MovieCredits>> getLiveMovieCredits(@Path("movie_id") Integer movie_id, @Query("api_key") String api_key);

    @GET("person/{person_id}")
    LiveData<ApiResponse<Actor>> getLivePerson(@Path("person_id") Integer person_id, @Query("api_key") String api_key);

    // GET /person/{person_id}/images
    @GET("person/{person_id}/images")
    LiveData<ApiResponse<ActorImagesResponse>> getLivePersonImages(@Path("person_id") Integer person_id, @Query("api_key") String api_key);


    @GET("search/person")
    LiveData<ApiResponse<ActorSearchResponse>> getLivePersonSearch(@Query("api_key") String api_key, @Query("query") String person);


    //region is optional
    //  @GET("movie/top_rated")
    // Call<MoviesPage> getNewTopRatedMovies(@Query("api_key") String api_key
    //   , @Query("page") Integer page, @Query("region") String region);


}
