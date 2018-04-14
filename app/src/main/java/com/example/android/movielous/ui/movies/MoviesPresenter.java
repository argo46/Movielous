package com.example.android.movielous.ui.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.movielous.Models.MoviePojo;
import com.example.android.movielous.Models.ResultPojo;
import com.example.android.movielous.Rest.ApiClient;
import com.example.android.movielous.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;


public class MoviesPresenter implements com.example.android.movielous.ui.movies.MoviesContract.Presenter {

    private final com.example.android.movielous.ui.movies.MoviesContract.View mMoviesView;


    public MoviesPresenter(@NonNull com.example.android.movielous.ui.movies.MoviesContract.View moviesView){
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        mMoviesView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void loadMovies(String sortBy, boolean isInit, int page) {

//        int page=1;
        if (isInit){page = 1;}

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //mLoadingPB.setVisibility(View.VISIBLE);
        mMoviesView.setLoadingIndicator(true);
        Call<MoviePojo> call = apiService.getMovies(sortBy,"", page);
        call.enqueue(new Callback<MoviePojo>() {
            @Override
            public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
//                mMoviesView.setLoadingIndicator(true);
                Integer statusCode = response.code();
                Log.d("MoviesActivity","Status Code : " + statusCode.toString());

                if(statusCode == 200){
                    MoviePojo movies = response.body();
                    if (isInit){
                        mMoviesView.showMovies(movies);
//                        mMovieAdapter.setmMovieData(movies);
                    } else{
//                        mMovieAdapter.addMovieData(movies);
                        mMoviesView.showMoreMovies(movies);
                    }

                    //mLoadingPB.setVisibility(View.INVISIBLE);
                    mMoviesView.setLoadingIndicator(false);
                    Log.d("MoviesActivity", "Received page = " + Integer.toString(movies.getPage()));
//                    loading = true;
                    mMoviesView.setPaginationBool(true);

                } else {
//                    showError();
                    mMoviesView.showError();
                }
            }

            @Override
            public void onFailure(Call<MoviePojo> call, Throwable t) {
//                showError();
                mMoviesView.showError();
                Log.e("MoviesActivity error : ", t.toString());
            }
        });
    }

    @Override
    public void openMovieDetail(ResultPojo movie) {
        mMoviesView.showDetailMovieUi(movie);
    }

    @Override
    public void openFavoriteMovie() {

    }
}
