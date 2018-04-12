package com.example.android.movielous;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movielous.Models.MovieReview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-Lenovo on 09/08/2017.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewAdapterViewHolder>{

    List<MovieReview> reviews = new ArrayList<>();

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mReviewer;
        public final TextView mContentReview;
        public MovieReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mReviewer = (TextView) itemView.findViewById(R.id.tv_name_reviewer_review);
            mContentReview = (TextView) itemView.findViewById(R.id.tv_content_review_review);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem,parent,false);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {
        String reviewer = reviews.get(position).getReviewerName();
        String contentReview = reviews.get(position).getContentReview();
        holder.mReviewer.setText(reviewer);
        holder.mContentReview.setText(contentReview);
    }

    @Override
    public int getItemCount() {
        if (null == reviews){
            return 0;
        }
        return reviews.size();
    }

    public void setReviewsData(ArrayList reviews){
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
