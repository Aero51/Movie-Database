package com.aero51.moviedatabase.repository.retrofit

import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel
import com.aero51.moviedatabase.utils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {


    @GET("/")
    fun getLiveMovieDetails(@Query("apikey") api_key: String, @Query("t") movieTitle: String): LiveData<ApiResponse<OmdbModel>>

}