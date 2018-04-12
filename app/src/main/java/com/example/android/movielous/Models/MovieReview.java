package com.example.android.movielous.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC-Lenovo on 08/08/2017.
 */

public class MovieReview implements Parcelable {
    private String reviewerName;
    private String contentReview;

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getContentReview() {
        return contentReview;
    }

    public void setContentReview(String contentReview) {
        this.contentReview = contentReview;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewerName);
        parcel.writeString(contentReview);
    }

    public MovieReview (Parcel parcel) {
        reviewerName = parcel.readString();
        contentReview = parcel.readString();
    }

    public MovieReview (){}

    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>(){

        @Override
        public MovieReview createFromParcel(Parcel parcel) {
            return new MovieReview(parcel);
        }

        @Override
        public MovieReview[] newArray(int i) {
            return new MovieReview[i];
        }
    };
}
