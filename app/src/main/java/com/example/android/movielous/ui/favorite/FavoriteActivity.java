package com.example.android.movielous.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.movielous.data.models.movies.Movies;
import com.example.android.movielous.R;
import com.example.android.movielous.data.db.MovieContract;
import com.example.android.movielous.ui.detailMovie.DetailMovieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity
        implements FavoriteMovieAdapter.FavoriteAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor>{


    @BindView(R.id.rv_movie_main) RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_fv) ProgressBar mLoadingPB;

    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    private static final int ID_FAV_MOVIE_LOADER = 24;


    public static final String[] MOVIE_FAVORITE_PROJECTION = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATE};

    public static final int INDEX_ID = 0;
    public static final int INDEX_COLUMN_TITLE = 1;
    public static final int INDEX_COLUMN_ORIGINAL_TITLE =2;
    public static final int INDEX_COLUMN_RELEASE_DATE = 3;
    public static final int INDEX_COLUMN_BACKDROP_PATH = 4;
    public static final int INDEX_COLUMN_POSTER_PATH = 5;
    public static final int INDEX_COLUMN_OVERVIEW = 6;
    public static final int INDEX_COLUMN_RATE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        int noColumn = getNoOfColumn();
        GridLayoutManager layoutManager = new GridLayoutManager(this,noColumn);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mFavoriteMovieAdapter = new FavoriteMovieAdapter(this);
        mRecyclerView.setAdapter(mFavoriteMovieAdapter);


        getSupportLoaderManager().initLoader(ID_FAV_MOVIE_LOADER, null, this);
    }

    private int getNoOfColumn(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumn = (int) (dpWidth/180);
        while(20 % noOfColumn != 0){
            noOfColumn++;
        }
        return noOfColumn;
    }

    private void showMovie() {
        /* First, hide the loading indicator */
        mLoadingPB.setVisibility(View.INVISIBLE);
        /* Finally, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        /* Then, hide the weather data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Finally, show the loading indicator */
        mLoadingPB.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_FAV_MOVIE_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri = MovieContract.MovieEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */
                return new CursorLoader(this,
                        forecastQueryUri,
                        MOVIE_FAVORITE_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoriteMovieAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) showMovie();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoriteMovieAdapter.swapCursor(null);
    }

    @Override
    public void onClick(String id) {

        String mId = id;
        Movies movie = new Movies();
        Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(mId).build();
        Cursor cursor = getContentResolver().query(uri, MOVIE_FAVORITE_PROJECTION,null,null,null);
        cursor.moveToFirst();
        if (cursor.getCount()!= 0){
            movie.setId(Integer.parseInt(cursor.getString(INDEX_ID)));
            movie.setTitle(cursor.getString(INDEX_COLUMN_TITLE));
            movie.setOriginalTitle(cursor.getString(INDEX_COLUMN_ORIGINAL_TITLE));
            movie.setReleaseDate(cursor.getString(INDEX_COLUMN_RELEASE_DATE));
            movie.setBackdropPath(cursor.getString(INDEX_COLUMN_BACKDROP_PATH));
            movie.setPosterPath(cursor.getString(INDEX_COLUMN_POSTER_PATH));
            movie.setOverview(cursor.getString(INDEX_COLUMN_OVERVIEW));
            movie.setVoteAverage(Float.parseFloat(cursor.getString(INDEX_COLUMN_RATE)));
        }
        Context context = this;
        Intent intent = new Intent(context,DetailMovieActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }
}
