package com.example.android.movielous.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC-Lenovo on 28/12/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit movies = null;



    public static Retrofit getClient(){
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (movies == null){
            movies = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return movies;
    }
}
