package com.example.android.movielous.ui.favorite;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movielous.R;
import com.example.android.movielous.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by PC-Lenovo on 08/08/2017.
 */

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>{

    final private FavoriteAdapterOnClickHandler mClickHandler;
    public interface FavoriteAdapterOnClickHandler{
        void onClick(String id);
    }

    private Cursor mCursor;

    public FavoriteMovieAdapter(FavoriteAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTitleTV;
        public final ImageView mMoviePosterIV;

        public FavoriteMovieViewHolder(View view){
            super(view);
            mMovieTitleTV = (TextView) view.findViewById(R.id.tv_movie_title);
            mMoviePosterIV = (ImageView)view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String id = mCursor.getString(FavoriteActivity.INDEX_ID);
            mClickHandler.onClick(id);

        }
    }
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem,parent,false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String movieTitle = mCursor.getString(FavoriteActivity.INDEX_COLUMN_TITLE);
        String imagePath = mCursor.getString(FavoriteActivity.INDEX_COLUMN_POSTER_PATH);
        holder.mMovieTitleTV.setText(movieTitle);
        URL imagePathUrl = NetworkUtils.buildImagePathUrl(imagePath,"w185");
        Picasso.with(holder.mMoviePosterIV.getContext()).load(imagePathUrl.toString()).into(holder.mMoviePosterIV);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void setMovieData (Cursor cursor){
        this.mCursor = cursor;
        notifyDataSetChanged();
    }
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

}
