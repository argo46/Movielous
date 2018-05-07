package com.example.android.movielous.ui.detailMovie;


import com.example.android.movielous.BasePresenter;
import com.example.android.movielous.BaseView;
import com.example.android.movielous.data.models.movies.Movies;
import com.example.android.movielous.data.models.reviews.ReviewHeader;
import com.example.android.movielous.data.models.videos.VideosHeader;

public interface DetailMovieContract {

    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showMovieUi(Movies movie);
        void showMovieReview(ReviewHeader reviewHeader);
        void showVideos(VideosHeader videosHeader);
        void showMoreReviewUi(ReviewHeader reviews);
        void checkFavorite();

    }

    interface Presenter extends BasePresenter {
        void loadData(int movie_id);
        void addToFavorite();
        void removeFromFavorite();
        void goToMoreReview(ReviewHeader reviews);
        void goToWatchVideo();
    }
}
