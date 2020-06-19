package com.aero51.moviedatabase.repository.retrofit;

import com.aero51.moviedatabase.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aero51.moviedatabase.utils.Constants.BASE_URL;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static synchronized TheMovieDbApi getApiService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(TheMovieDbApi.class);
    }
}