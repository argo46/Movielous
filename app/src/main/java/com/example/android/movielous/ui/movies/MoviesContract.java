package com.example.android.movielous.ui.movies;

import com.example.android.movielous.BasePresenter;
import com.example.android.movielous.BaseView;
import com.example.android.movielous.Models.MoviePojo;
import com.example.android.movielous.Models.ResultPojo;

public interface MoviesContract {

    interface  View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showMovies(MoviePojo movies);
        void showMoreMovies(MoviePojo movies);
        void setPaginationBool(boolean loading);
        void showError();
        void showDetailMovieUi(ResultPojo movie);
        void showFavoriteMovieUi();

    }

    interface Presenter extends BasePresenter {
        void loadMovies(String sortBy, boolean isInit, int page);
        void openMovieDetail(ResultPojo movie);
        void openFavoriteMovie();

    }
}
