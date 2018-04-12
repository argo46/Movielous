package com.example.android.movielous.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by PC-Lenovo on 08/08/2017.
 */

public class MovieProvider extends ContentProvider{

    public static final int CODE_MOVIE = 100;
    public static final int CODE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUiMathcer();
    private MovieDBHelper mOpenHelper;

    public static UriMatcher buildUiMathcer(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, CODE_MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/*", CODE_MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrer) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE_WITH_ID: {

                String id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{id};

                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrer);

                break;
            }

            case CODE_MOVIE:{
                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrer);
                break;
            }

            default: throw new UnsupportedOperationException("Unknown Uri "+ uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri newUri;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (selection == null){selection = "1";}

        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri "+ uri);
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
