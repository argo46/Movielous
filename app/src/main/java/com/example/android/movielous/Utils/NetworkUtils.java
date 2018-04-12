package com.example.android.movielous.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by user on 7/9/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String API_KEY = "";
    private static int page = 1;


    public static URL buildUrl(String shortBy, int page){
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(shortBy)
                .appendQueryParameter("api_key",API_KEY)
                .appendQueryParameter("language","en")
                .appendQueryParameter("page", Integer.toString(page)).build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildImagePathUrl(String path,String size){
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(size).appendEncodedPath(path).build();

        URL url = null;
        try {
            url =  new URL(builtUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static URL buildReviewUrl(String id){
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath("reviews")
                .appendQueryParameter("api_key",API_KEY)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static URL buildVideoUrl(String id){
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath("videos")
                .appendQueryParameter("api_key",API_KEY)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static Uri buildYoutubeVideoUri(String key){
        Uri builtUri = Uri.parse("https://www.youtube.com/watch").buildUpon()
                .appendQueryParameter("v",key)
                .build();
        return builtUri;
    }

    public static URL buildYoutubeTumbnailVideoUrl(String key){
       // https://img.youtube.com/vi/<insert-youtube-video-id-here>/mqdefault.jpg
        Uri builtUri = Uri.parse("https://img.youtube.com/vi").buildUpon()
                .appendEncodedPath(key)
                .appendEncodedPath("mqdefault.jpg")
                .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            String response;
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                response = scanner.next();
                scanner.close();
                in.close();
                return response;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
