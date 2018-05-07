package com.example.android.movielous.ui.movies;

import com.example.android.movielous.BasePresenter;
import com.example.android.movielous.BaseView;
import com.example.android.movielous.data.models.movies.MoviesHeader;
import com.example.android.movielous.data.models.movies.Movies;

public interface MoviesContract {

    interface  View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showMovies(MoviesHeader movies);
        void showMoreMovies(MoviesHeader movies);
        void setPaginationBool(boolean loading);
        void showError();
        void showDetailMovieUi(Movies movie);
        void showFavoriteMovieUi();

    }

    interface Presenter extends BasePresenter {
        void loadMovies(String sortBy, boolean isInit, int page);
        void openMovieDetail(Movies movie);
        void openFavoriteMovie();

    }
}
