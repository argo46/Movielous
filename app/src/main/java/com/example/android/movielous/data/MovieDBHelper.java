package com.example.android.movielous.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC-Lenovo on 08/08/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movie.db";

    public static final int DATABASE_VERSION = 1;

    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID                    + " TEXT PRIMARY KEY, " +
                MovieContract.MovieEntry.COLUMN_TITLE           + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE  + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE    + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATH   + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH     + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_OVERVIEW        + " TEXT NOT NULL, "    +
                MovieContract.MovieEntry.COLUMN_RATE            + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
