package com.example.android.movielous.ui.detailMovie;


import com.example.android.movielous.BasePresenter;
import com.example.android.movielous.BaseView;
import com.example.android.movielous.Models.ResultPojo;
import com.example.android.movielous.Models.reviews.ReviewHeader;
import com.example.android.movielous.Models.videos.VideosHeader;

public interface DetailMovieContract {

    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showMovieUi(ResultPojo movie);
        void showMovieReview(ReviewHeader reviewHeader);
        void showVideos(VideosHeader videosHeader);
        void checkFavorite();

    }

    interface Presenter extends BasePresenter {
        void loadData(int movie_id);
        void addToFavorite();
        void removeFromFavorite();
        void goToMoreReview();
        void goToWatchVideo();
    }
}
