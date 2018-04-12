package com.example.android.movielous.Utils;

import android.content.Context;

import com.example.android.movielous.Models.MovieModel;
import com.example.android.movielous.Models.MovieReview;
import com.example.android.movielous.Models.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/9/2017.
 */

public class TheMovieDBJsonUtils {
    private static final String OWM_TITLE = "title";
    private static final String OWM_POSTER = "poster_path";
    private static final String OWM_MOVIE_ID = "id";
    private static final String OWM_RESULT = "results";
    private static final String OWM_ORIGINAL_TITLE = "original_title";
    private static final String OWM_ORIGINAL_BACKDROP = "backdrop_path";
    private static final String OWM_OVERVIEW = "overview";
    private static final String OWM_RATING = "vote_average";
    private static final String OWM_RELEASE = "release_date";

    private static final String OWM_TOTAL_RESULT_REVIEW = "total_results";
    private static final String OWM_NAME_REVIEWER = "author";
    private static final String OWM_CONTENT_REVIEW = "content";

    private static final String OWM_KEY_VIDEO = "key";
    private static final String OWM_VIDEO_NAME = "name";
    private static final String OWM_VIDEO_SITE = "site";
    private static final String OWM_VIDEO_TYPE = "type";


    public static List getSimpleMovieListFromJson(Context context, String movieDatajson)
            throws JSONException {


        List<MovieModel> movies = new ArrayList();
        JSONObject movieJson = new JSONObject(movieDatajson);

        if (movieJson.has("status_code")){
            int errorCode = movieJson.getInt("status");

            switch (errorCode){
                case 200 :
                    break;
                case 401 :
                    return null;
                case 404 :
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULT);

        for (int i = 0; i < movieArray.length(); i++){
            JSONObject movieObject = movieArray.getJSONObject(i);

            MovieModel movie = new MovieModel();
            movie.setTitle(movieObject.getString(OWM_TITLE));
            movie.setPosterPath(movieObject.getString(OWM_POSTER));
            movie.setId(movieObject.getString(OWM_MOVIE_ID));
            movie.setBackDropPath(movieObject.getString(OWM_ORIGINAL_BACKDROP));
            movie.setOriginalTitle(movieObject.getString(OWM_ORIGINAL_TITLE));
            movie.setReleaseDate(movieObject.getString(OWM_RELEASE));
            movie.setRate(movieObject.getString(OWM_RATING));
            movie.setOverview(movieObject.getString(OWM_OVERVIEW));
            movies.add(movie);
        }



            return movies;
    }

    public static List getSimpleReviewFromJson (Context context, String movieIdJson)
    throws JSONException {



        List<MovieReview> reviews = new ArrayList();
        JSONObject movieJson = new JSONObject(movieIdJson);

        if (movieJson.has("status_code")){
            int errorCode = movieJson.getInt("status");

            switch (errorCode){
                case 200 :
                    break;
                case 401 :
                    return null;
                case 404 :
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULT);

        for (int i = 0; i < movieArray.length(); i++){
            JSONObject movieObject = movieArray.getJSONObject(i);

            MovieReview review = new MovieReview();
            review.setReviewerName(movieObject.getString(OWM_NAME_REVIEWER));
            review.setContentReview(movieObject.getString(OWM_CONTENT_REVIEW));
            reviews.add(review);
    }
    return reviews;
    }

    public static List getSimpleVideoFromJson (Context context, String movieIdJson)
            throws JSONException {



        List<MovieVideo> videos = new ArrayList();
        JSONObject movieJson = new JSONObject(movieIdJson);

        if (movieJson.has("status_code")){
            int errorCode = movieJson.getInt("status");

            switch (errorCode){
                case 200 :
                    break;
                case 401 :
                    return null;
                case 404 :
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULT);

        for (int i = 0; i < movieArray.length(); i++){
            JSONObject movieObject = movieArray.getJSONObject(i);

            MovieVideo video = new MovieVideo();
            video.setKey(movieObject.getString(OWM_KEY_VIDEO));
            video.setName(movieObject.getString(OWM_VIDEO_NAME));
            video.setSite(movieObject.getString(OWM_VIDEO_SITE));
            video.setType(movieObject.getString(OWM_VIDEO_TYPE));
            videos.add(video);
        }
        return videos;
    }

//    public static MovieModel getSimpleMovieDetailFromJson (Context context, String movieIdJson)
//    throws JSONException {
//
//        MovieModel movie = new MovieModel();
//
//        JSONObject movieDetailJson = new JSONObject(movieIdJson);
//
//        if (movieDetailJson.has("status_code")){
//            int errorCode = movieDetailJson.getInt("status_code");
//
//            switch (errorCode){
//                case 200:
//                    break;
//                case 401 :
//                    return null;
//                case 404 :
//                    return null;
//            }
//        }
//
//        movie.setBackDropPath(movieDetailJson.getString(OWM_ORIGINAL_BACKDROP));
//        movie.setOriginalTitle(movieDetailJson.getString(OWM_ORIGINAL_TITLE));
//        movie.setReleaseDate(movieDetailJson.getString(OWM_RELEASE));
//        movie.setRate(movieDetailJson.getString(OWM_RATING));
//        movie.setOverview(movieDetailJson.getString(OWM_OVERVIEW));
//
//        return movie;
//    }

}
