package com.example.android.movielous.data.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by PC-Lenovo on 08/08/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.movielous";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_BACKDROP_PATH = "backrop_path";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATE = "rate";

        public static Uri buildMovieUriWithId (String id){
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }
    }
}
