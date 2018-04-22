package com.example.android.movielous.Models.videos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosHeader implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Videos> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Videos> getResults() {
        return results;
    }

    public void setResults(List<Videos> results) {
        this.results = results;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(this.results);
    }

    public VideosHeader() {
    }

    protected VideosHeader(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Videos.CREATOR);
    }

    public static final Parcelable.Creator<VideosHeader> CREATOR = new Parcelable.Creator<VideosHeader>() {
        @Override
        public VideosHeader createFromParcel(Parcel source) {
            return new VideosHeader(source);
        }

        @Override
        public VideosHeader[] newArray(int size) {
            return new VideosHeader[size];
        }
    };
}
