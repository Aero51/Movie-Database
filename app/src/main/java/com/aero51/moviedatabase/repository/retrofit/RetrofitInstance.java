package com.aero51.moviedatabase.repository.retrofit;

import com.aero51.moviedatabase.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aero51.moviedatabase.utils.Constants.EPG_BASE_URL;
import static com.aero51.moviedatabase.utils.Constants.TMDB_BASE_URL;

public class RetrofitInstance {

    private static Retrofit tmdbRetrofit = null;
    private static Retrofit epgRetrofit = null;

    public static synchronized TheMovieDbApi getTmdbApiService() {
        if (tmdbRetrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            tmdbRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .client(okHttpClient)
                    .build();

        }
        return tmdbRetrofit.create(TheMovieDbApi.class);
    }

    public static synchronized EpgApi getEpgApiService() {
        if (epgRetrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            epgRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(EPG_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .client(okHttpClient)
                    .build();

        }
        return epgRetrofit.create(EpgApi.class);
    }


}