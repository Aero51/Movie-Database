package com.aero51.moviedatabase.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    @GET("movie/top_rated")
    Call<Top_Rated_Movies_Page> getTopRatedMovies(@Query("api_key") String api_key
            , @Query("page") Integer page);

    // https://api.themoviedb.org/3/movie/top_rated?api_key=8ba72532be79fd82366e924e791e0c71&language=en-US&page=1

    //  @GET("group/{id}/users")
    // Call<List<User>> groupList(@Path("id") int groupId);

}