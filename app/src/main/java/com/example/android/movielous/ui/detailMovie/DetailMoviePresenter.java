package com.example.android.movielous.ui.detailMovie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.movielous.Models.reviews.ReviewHeader;
import com.example.android.movielous.Models.videos.VideosHeader;
import com.example.android.movielous.Rest.ApiClient;
import com.example.android.movielous.Rest.ApiInterface;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Observable<ReviewHeader> reviews = apiService.getReviews(movie_id);
        Observable<VideosHeader> videos = apiService.getVideos(movie_id);

       // Observable<ArrayList<Object>> loadData =
                Observable.zip(reviews, videos, (reviewHeader, videosHeader) -> {
                    ArrayList<Object> data = new ArrayList<>();
                    data.add(reviewHeader);
                    data.add(videosHeader);
                    return data;
                })
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Object> objects) {
                        ReviewHeader reviews = (ReviewHeader) objects.get(0);
                        VideosHeader video = (VideosHeader) objects.get(1);

                        mDetailMovieView.showMovieReview(reviews);
                        mDetailMovieView.showVideos(video);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Detail Activity", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//                Observable.zip(reviews,
//                videos,
//                (reviewHeader, videosHeader) -> {
//                    ArrayList<Object>  response= new ArrayList<Object>();
//                    response.add(reviewHeader);
//                    response.add(videosHeader);
//                    return null;
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
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
