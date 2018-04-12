package com.example.android.movielous.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 7/11/2017.
 */

public class MovieModel implements Parcelable{
    private String title;
    private String originalTitle;
    private String releaseDate;
    private String backDropPath;
    private String posterPath;
    private String overview;
    private String rate;
    private String id;


    public String getBackDropPath() {
        return backDropPath;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overView) {
        this.overview = overView;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel destParcel, int flags) {
        destParcel.writeString(title);
        destParcel.writeString(originalTitle);
        destParcel.writeString(releaseDate);
        destParcel.writeString(backDropPath);
        destParcel.writeString(posterPath);
        destParcel.writeString(overview);
        destParcel.writeString(rate);
        destParcel.writeString(id);
    }

    public MovieModel (Parcel parcel){
        title = parcel.readString();
        originalTitle = parcel.readString();
        releaseDate = parcel.readString();
        backDropPath = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        rate = parcel.readString();
        id = parcel.readString();
    }
    public MovieModel(){}

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>(){

        @Override
        public MovieModel createFromParcel (Parcel parcel){
            return new MovieModel(parcel);
        }

        @Override
        public MovieModel[] newArray(int i) {
            return new MovieModel[0];
        }
    };
}
