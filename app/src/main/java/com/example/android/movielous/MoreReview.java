package com.example.android.movielous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MoreReview extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieReviewsAdapter movieReviewsAdapter;
    private int mPosition = RecyclerView.NO_POSITION;


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

        ArrayList list = new ArrayList<>();
        Intent intentFromParent = getIntent();
        if (intentFromParent != null){
            if (intentFromParent.hasExtra("Reviews")){
                list = intentFromParent.getParcelableArrayListExtra("Reviews");
            }
        }

        movieReviewsAdapter.setReviewsData(list);
    }
}
