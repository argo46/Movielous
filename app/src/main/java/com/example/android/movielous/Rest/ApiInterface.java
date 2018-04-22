package com.example.android.movielous.Rest;

import com.example.android.movielous.Models.MoviePojo;
import com.example.android.movielous.Models.reviews.ReviewHeader;
import com.example.android.movielous.Models.videos.VideosHeader;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PC-Lenovo on 28/12/2017.
 */

public interface ApiInterface {


    @GET("movie/{sortBy}")
    Observable<MoviePojo> getMovies(@Path("sortBy") String sortBy, @Query("page") int page);

    @GET("movie/{movie_id}/reviews")
    Observable<ReviewHeader> getReviews(@Path("movie_id") int id);

    @GET("movie/{movie_id}/videos")
    Observable<VideosHeader> getVideos(@Path("movie_id") int id);

}
