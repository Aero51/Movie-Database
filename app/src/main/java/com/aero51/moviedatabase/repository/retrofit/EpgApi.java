package com.aero51.moviedatabase.repository.retrofit;


import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface EpgApi {


    @GET("program/{channel_id}")
    Call<List<EpgProgram>> getProgramsForChannel(@Path("channel_id")String channel_id);
    //LiveData<ApiResponse<Actor>> getLivePerson(@Path("person_id")Integer person_id, @Query("api_key") String api_key );

    @GET("channels")
    Call<List<EpgChannel>> getChannels();

}
