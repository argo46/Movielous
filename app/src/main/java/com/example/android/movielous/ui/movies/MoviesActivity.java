package com.example.android.movielous.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.movielous.FavoriteActivity;
import com.example.android.movielous.Models.MoviePojo;
import com.example.android.movielous.Models.ResultPojo;
import com.example.android.movielous.R;
import com.example.android.movielous.ui.detailMovie.DetailMovieActivity;
import com.facebook.stetho.Stetho;

import static com.google.common.base.Preconditions.checkNotNull;


public class MoviesActivity extends AppCompatActivity
        implements MoviesAdapter.MovieAdapterOnClickHandler, MoviesContract.View {

    private MoviesContract.Presenter mPresenter;
    private MoviesPresenter mMoviesPresenter;
    private RecyclerView mRecycleView;
    private MoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingPB;
    private TextView mErrorMessage, mSortByTitle;
    private Button  mGoToFavorite;
    private android.support.v7.widget.Toolbar toolbar;
    private static String sortBy = "popular";
    private static int page = 1;
    private static final String popular = "popular";
    private static final String top_rated = "top_rated";
    private static final String upcoming = "upcoming";
    private static final String favorite = "favorite";

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Stetho.initializeWithDefaults(this);
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        mRecycleView = (RecyclerView) findViewById(R.id.rv_movie_main);
        mLoadingPB = (ProgressBar) findViewById(R.id.pb_loading);
        mErrorMessage = (TextView)findViewById(R.id.tv_error);
        mSortByTitle = (TextView)findViewById(R.id.tv_short_by_title);
        mGoToFavorite = (Button)findViewById(R.id.go_to_favorite);

        mMoviesPresenter = new MoviesPresenter(this);


        int noColumn = getNoOfColumn();
        final GridLayoutManager layoutManager = new GridLayoutManager(this,noColumn);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(this);
        mRecycleView.setAdapter(mMoviesAdapter);
        mSortByTitle.setText(getResources().getString(R.string.popular));


        /**
         * Listener for endless scroll
         */
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();


                    if (loading)
                    {
                        if ( (visibleItemCount + firstVisibleItem) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data


                            page ++;
//                            fetchData(sortBy,false);
                            mPresenter.loadMovies(sortBy,false, page);
                        }
                    }
                }
            }
        });

//        fetchData(sortBy, true);
        mPresenter.loadMovies(sortBy,true, page);
    }

    /**
     * get number of gridview column depends on the screen size.
     */
    private int getNoOfColumn(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumn = (int) (dpWidth/180);
        while(20 % noOfColumn != 0){
            noOfColumn++;
        }
        return noOfColumn;
    }

    /**
     * Click Handler for favorite Button(appear if no internet)
     */
    public void goToFavorite(View view){
        mPresenter.openFavoriteMovie();
    }

    @Override
    public void showFavoriteMovieUi() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    /**
     * Click Handler for recycler view item.
     * @param movie
     */
    @Override
    public void onClick(ResultPojo movie) {
        mPresenter.openMovieDetail(movie);
    }

    @Override
    public void showDetailMovieUi(ResultPojo movie) {
        Intent intent = new Intent(this,DetailMovieActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.movie_main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_srt_by_popular :
                sortBy = popular;
                mSortByTitle.setText(getResources().getString(R.string.popular));
//                fetchData(sortBy, true);
                mPresenter.loadMovies(sortBy,true, page);
                return true;
            case R.id.menu_srt_by_top_rated :
                sortBy = top_rated;
                mSortByTitle.setText(getResources().getString(R.string.top_rated));
//                fetchData(sortBy, true);
                mPresenter.loadMovies(sortBy,true, page);
                return true;
            case R.id.menu_srt_by_upcoming :
                sortBy = upcoming;
                mSortByTitle.setText(getResources().getString(R.string.upcoming));
//                fetchData(sortBy, true);
                mPresenter.loadMovies(sortBy,true, page);
                return true;
            case  R.id.menu_refresh :
//                fetchData(sortBy, true);
                mPresenter.loadMovies(sortBy,true, page);
                return true;
            case R.id.menu_favorite:
                mPresenter.openFavoriteMovie();
                return true;
            default :
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active){
            mLoadingPB.setVisibility(View.VISIBLE);
        } else {
            mLoadingPB.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showMovies(MoviePojo movies) {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mGoToFavorite.setVisibility(View.INVISIBLE);

        mRecycleView.setVisibility(View.VISIBLE);

        mMoviesAdapter.setmMovieData(movies);
    }

    @Override
    public void showMoreMovies(MoviePojo movies) {
        mMoviesAdapter.addMovieData(movies);
    }

    @Override
    public void setPaginationBool(boolean loading) {
        this.loading = loading;
    }

    @Override
    public void showError() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mGoToFavorite.setVisibility(View.VISIBLE);

        mRecycleView.setVisibility(View.INVISIBLE);
    }



    @Override
    public void setPresenter(com.example.android.movielous.ui.movies.MoviesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
