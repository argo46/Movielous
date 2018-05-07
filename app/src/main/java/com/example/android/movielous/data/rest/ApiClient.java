package com.example.android.movielous.data.rest;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC-Lenovo on 28/12/2017.
 */

public class ApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String api_key = "";
    private static Retrofit movies = null;



    public static Retrofit getClient(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl httpUrlOriginal = original.url();

            HttpUrl url = httpUrlOriginal.newBuilder()
                    .addQueryParameter("api_key", api_key)
                    .build();

            Request request = original.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        })
                .build();

        if (movies == null){
            movies = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return movies;
    }
}
