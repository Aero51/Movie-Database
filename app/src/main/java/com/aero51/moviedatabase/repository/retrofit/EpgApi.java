package com.aero51.moviedatabase.repository.retrofit;


import androidx.lifecycle.LiveData;

import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.ApiResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface EpgApi {


    @GET("program/{channel_id}")
    LiveData<ApiResponse<List<EpgProgram>>> getLiveProgramsForChannel(@Path("channel_id") String channel_id);
    //Call<List<EpgProgram>> getProgramsForChannel(@Path("channel_id") String channel_id);
    //LiveData<ApiResponse<Actor>> getLivePerson(@Path("person_id")Integer person_id, @Query("api_key") String api_key );

    @GET("program/{channel_id}")
    Call<List<EpgProgram>> getProgramsForChannel(@Path("channel_id") String channel_id);

    @GET("channels")
    Call<List<EpgChannel>> getChannels();


    @GET("channels")
    LiveData<ApiResponse<List<EpgChannel>>> getLiveChannels();

    @GET("programs/cro")
    LiveData<ApiResponse<List<EpgProgram>>> getLiveCroPrograms();
}
