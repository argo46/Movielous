package com.example.android.movielous.utils;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 7/9/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";


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
}
