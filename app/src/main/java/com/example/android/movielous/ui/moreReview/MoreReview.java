package com.example.android.movielous.ui.moreReview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.movielous.R;
import com.example.android.movielous.data.models.reviews.ReviewHeader;

public class MoreReview extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieReviewsAdapter movieReviewsAdapter;
    private ReviewHeader reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_review);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        movieReviewsAdapter = new MovieReviewsAdapter();
        mRecyclerView.setAdapter(movieReviewsAdapter);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null){
            if (intentFromParent.hasExtra("Reviews")){
                reviews = intentFromParent.getParcelableExtra("Reviews");
            }
        }

        movieReviewsAdapter.setReviewsData(reviews.getResults());
    }
}
