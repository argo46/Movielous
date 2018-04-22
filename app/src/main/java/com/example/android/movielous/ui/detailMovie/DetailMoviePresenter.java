package com.example.android.movielous.ui.detailMovie;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailMoviePresenter implements DetailMovieContract.Presenter{

    private final DetailMovieContract.View mDetailMovieView;

    public DetailMoviePresenter(@NonNull DetailMovieContract.View detailMovieView) {DetailMovieContract.View mDetailMovieView1;
        mDetailMovieView = checkNotNull(detailMovieView, "detailMovie view cannot be null");

        mDetailMovieView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(int movie_id) {

    }

    @Override
    public void addToFavorite() {

    }

    @Override
    public void removeFromFavorite() {

    }

    @Override
    public void goToMoreReview() {

    }

    @Override
    public void goToWatchVideo() {

    }
}
