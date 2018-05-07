package com.example.android.movielous.ui.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.movielous.data.models.movies.MoviesHeader;
import com.example.android.movielous.data.models.movies.Movies;
import com.example.android.movielous.data.rest.ApiClient;
import com.example.android.movielous.data.rest.ApiInterface;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;


public class MoviesPresenter implements MoviesContract.Presenter {

    private final MoviesContract.View mMoviesView;


    public MoviesPresenter(@NonNull MoviesContract.View moviesView){
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        mMoviesView.setPresenter(this);
    }

    @Override
    public void start() {
    }


    @Override
    public void loadMovies(String sortBy, boolean isInit, int page) {

        if (isInit){page = 1;}

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        mMoviesView.setLoadingIndicator(true);
        Observable<MoviesHeader> obs1 = apiService.getMovies(sortBy, page);
        obs1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MoviesHeader>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(MoviesHeader movies) {
                if (isInit){
                    mMoviesView.showMovies(movies);
                } else {
                    mMoviesView.showMoreMovies(movies);
                }

                mMoviesView.setLoadingIndicator(false);
                Log.d("MoviesActivity", "Received page = " + Integer.toString(movies.getPage()));
                mMoviesView.setPaginationBool(true);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MoviesActivity Error", e.toString());
                mMoviesView.setLoadingIndicator(false);
                mMoviesView.showError();
            }

            @Override
            public void onComplete() { }
        });

    }

    @Override
    public void openMovieDetail(Movies movie) {
        mMoviesView.showDetailMovieUi(movie);
    }

    @Override
    public void openFavoriteMovie() {
        mMoviesView.showFavoriteMovieUi();
    }
}
