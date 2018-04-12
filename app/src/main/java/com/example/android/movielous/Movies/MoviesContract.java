package com.example.android.movielous.Movies;

import com.example.android.movielous.BasePresenter;
import com.example.android.movielous.BaseView;
import com.example.android.movielous.Models.MoviePojo;

public interface MoviesContract {

    interface  View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showMovies(MoviePojo movies);
        void showMoreMovies(MoviePojo movies);
        void setPaginationBool(boolean loading);
        void showError();

    }

    interface Presenter extends BasePresenter {
        void loadMovies(String sortBy, boolean isInit, int page);


    }
}
