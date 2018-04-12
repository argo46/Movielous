package com.example.android.movielous.Rest;

import com.example.android.movielous.Models.MoviePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PC-Lenovo on 28/12/2017.
 */

public interface ApiInterface {
    String api_key = "";
    @GET("movie/{sortBy}")
//    Call<MoviePojo> getMovies(@Path("sortBy") String sortBy, @Query(("api_key"))String api_key, @Query("page") int page);
    Call<MoviePojo> getMovies(@Path("sortBy") String sortBy, @Query(("api_key"))String api_key, @Query("page") int page);

//    https://api.themoviedb.org/3/movie/upcoming?api_key=<<api_key>>&language=en-US&page=1
//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("movie/top_rated")
    Call<MoviePojo> getTopRated();

    @GET("movie/upcoming")
    Call<List<MoviePojo>> getUpcoming();
}
