package com.example.android.movielous;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movielous.Models.MoviePojo;
import com.example.android.movielous.Models.ResultPojo;
import com.example.android.movielous.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/9/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    MoviePojo respon= new MoviePojo();
    List<ResultPojo> movies = new ArrayList();
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler{
        void onClick(ResultPojo movie);
    }


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView mMovieTitleTV;
    final ImageView mMoviePosterIV;

    MovieAdapterViewHolder(View view){
        super(view);
        mMovieTitleTV = (TextView) view.findViewById(R.id.tv_movie_title);
        mMoviePosterIV = (ImageView)view.findViewById(R.id.iv_movie_poster);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ResultPojo movie = movies.get(getAdapterPosition());
        mClickHandler.onClick(movie);
    }
}

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem,parent,false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movieTitle = movies.get(position).getTitle();
        String imagePath = movies.get(position).getPosterPath();
        holder.mMovieTitleTV.setText(movieTitle);
        URL imagePathUrl = NetworkUtils.buildImagePathUrl(imagePath,"w185");
        Picasso.with(holder.mMoviePosterIV.getContext()).load(imagePathUrl.toString()).into(holder.mMoviePosterIV);
    }

    @Override
    public int getItemCount() {
        if(null == movies){
            return 0;
        }
        return movies.size();
    }

    public void setmMovieData(MoviePojo movies) {
        this.respon = movies;
        this.movies = respon.getResults();
        notifyDataSetChanged();
    }

    public void addMovieData(MoviePojo movies){
        this.movies.addAll(movies.getResults());
        notifyDataSetChanged();
    }

}
