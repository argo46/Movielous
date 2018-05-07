package com.example.android.movielous.ui.detailMovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.movielous.R;
import com.example.android.movielous.Utils.NetworkUtils;
import com.example.android.movielous.data.db.MovieContract;
import com.example.android.movielous.data.models.movies.Movies;
import com.example.android.movielous.data.models.reviews.ReviewHeader;
import com.example.android.movielous.data.models.reviews.Reviews;
import com.example.android.movielous.data.models.videos.Videos;
import com.example.android.movielous.data.models.videos.VideosHeader;
import com.example.android.movielous.ui.moreReview.MoreReview;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class DetailMovieActivity extends AppCompatActivity
        implements DetailMovieContract.View{


    private DetailMovieContract.Presenter mPresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private ProgressBar mLoading;
    private TextView mTitleAndRelease, mRates, mRelease, mReviewerName, mReview, mOtherReview;
    private TextView mDescription;
    private ImageView mBackdrop, mLikeButton;
    private ImageButton mVideo1;
    private Movies movie;

    public static final String[] MOVIE_DETAIL_PROJECTION = {
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        mLoading = (ProgressBar) findViewById(R.id.pb_detail_loading);
        mTitleAndRelease = (TextView)findViewById(R.id.tv_movie_title_detail);
        mRates = (TextView)findViewById(R.id.tv_rates);
        mDescription = (TextView) findViewById(R.id.tv_description);
        mBackdrop = (ImageView)findViewById(R.id.iv_backdrop);
        mRelease = (TextView)findViewById(R.id.tv_release);
        mReviewerName = (TextView)findViewById(R.id.tv_name_reviewer);
        mReview = (TextView)findViewById(R.id.tv_content_review);
        mVideo1 = (ImageButton) findViewById(R.id.iv_video1);
        mLikeButton = (ImageView)findViewById(R.id.iv_like_button);
        mOtherReview = (TextView)findViewById(R.id.other_review);


        mDetailMoviePresenter = new DetailMoviePresenter(this);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null){
            if (intentFromParent.hasExtra("Movie")){
                movie = intentFromParent.getParcelableExtra("Movie");
            }
        }


        showMovieUi(movie);


        //getSupportLoaderManager().initLoader(23, null, this);
        mPresenter.loadData(movie.getId());
        checkFavourite(Integer.toString(movie.getId()));


    }

    public void youtubeVideo(String key){
        Uri ytUrl = NetworkUtils.buildYoutubeVideoUri(key);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(ytUrl);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(DetailMovieActivity.class.getSimpleName(), "Couldn't call " + ytUrl.toString() + ", no receiving apps installed!");
        }
    }


    public void addTofavourite(Movies movie) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry._ID, movie.getId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RATE, Float.toString(movie.getVoteAverage()));

        this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
        checkFavourite(Integer.toString(movie.getId()));
    }

    private boolean removeFromFavourite(Movies movie) {
        int response = this.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry._ID + "=" + movie.getId(),null);
        return response > 0;
    }

    public void checkFavourite(String id){
        Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(id)
                .build();
        Cursor cursor = this.getContentResolver().query(uri, MOVIE_DETAIL_PROJECTION, null, null, null);
        if (cursor.getCount()==0){
            mLikeButton.setImageResource(R.drawable.like_button_off);
            mLikeButton.setOnClickListener(view -> addTofavourite(movie));
        } else {
            mLikeButton.setImageResource(R.drawable.like_button_active);
            mLikeButton.setOnClickListener(view -> {
                boolean success = removeFromFavourite(movie);
                if (success){mLikeButton.setImageResource(R.drawable.like_button_off);}
            });
        }
        cursor.close();
    }


//    public void goToMoreReview(View view) {
//
//    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMovieUi(Movies movie) {
        String year = movie.getReleaseDate().substring(0,4);
        mTitleAndRelease.setText(movie.getOriginalTitle().concat(" (").concat(year).concat(")"));
        mRates.setText(Float.toString(movie.getVoteAverage()));
        mDescription.setText(movie.getOverview());
        mRelease.setText(getResources().getString(R.string.release).concat(" : ").concat(movie.getReleaseDate()));
        URL imagePathUrl = NetworkUtils.buildImagePathUrl(movie.getBackdropPath(),"w500");
        Picasso.with(DetailMovieActivity.this).load(imagePathUrl.toString()).into(mBackdrop);
    }

    @Override
    public void showMovieReview(ReviewHeader reviewHeader) {
       List<Reviews> reviews = reviewHeader.getResults();

        if (reviews != null && reviews.size() != 0){
            Reviews review = reviews.get(0);
            mReviewerName.setText("A review by ".concat(review.getAuthor()));
            String contentReview = review.getContent();
            if (contentReview.length()>250){contentReview = contentReview.substring(0,250).concat("...");}
            mReview.setText(contentReview);
            mOtherReview.setVisibility(View.VISIBLE);
            mOtherReview.setOnClickListener(view -> {
                mPresenter.goToMoreReview(reviewHeader);
            });
        } else {
            mReviewerName.setText("No review yet");
            mOtherReview.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void showVideos(VideosHeader videosHeader) {

        List<Videos> videos = videosHeader.getResults();

        if (videos != null && videos.size() != 0){
            Videos video = videos.get(0);
            if (video.getSite().equals("YouTube")){
                final String key = video.getKey();
                URL imagePathUrl = NetworkUtils.buildYoutubeTumbnailVideoUrl(key);
                Picasso.with(DetailMovieActivity.this).load(imagePathUrl.toString()).into(mVideo1);

                mVideo1.setOnClickListener(view -> {
                    try {
                        youtubeVideo(key);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }

    }

    @Override
    public void showMoreReviewUi(ReviewHeader reviews) {
        Intent intent = new Intent(this, MoreReview.class);
        intent.putExtra("Reviews",reviews);
        startActivity(intent);
    }


    @Override
    public void checkFavorite() {

    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }




//    public void goToMoreVideo(View view) {
//        //not implemented yet, sorry
//    }
}
