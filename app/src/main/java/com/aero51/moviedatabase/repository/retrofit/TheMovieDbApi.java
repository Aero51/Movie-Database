package com.aero51.moviedatabase.repository.retrofit;

import com.aero51.moviedatabase.repository.model.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMoviesPage;

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
    Call<MovieCredits> getMovieCredits(@Query("api_key") String api_key,@Path("movie_id")Integer movie_id);

}
