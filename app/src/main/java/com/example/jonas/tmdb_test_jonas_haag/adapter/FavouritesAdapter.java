package com.example.jonas.tmdb_test_jonas_haag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonas.tmdb_test_jonas_haag.DAO.FavouriteMovie;
import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter  extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    List<FavouriteMovie> favourites;
    private LayoutInflater mInflater;
    FavouriteMovie movie;
    Context c;
    private FavouritesAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public FavouritesAdapter(Context context) {
        this.favourites = new ArrayList<>();
        c = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void getFavourites(List<FavouriteMovie> results){
        favourites = results;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_row, parent, false);
        return new FavouritesAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(FavouritesAdapter.ViewHolder holder, int position) {
        movie = favourites.get(position);
        holder.title.setText(movie.getTitle());
        holder.voteStat.setText(movie.getAverage() + Constant.AVERAGE_VOTE_OF_10);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return favourites.size();
    }

    // allows clicks events to be caught
    public void setClickListener(FavouritesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickFavourites(View view, int position, FavouriteMovie movie);
    }

    public FavouriteMovie getWordAtPosition (int position) {
        return favourites.get(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView voteStat;

        ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.card_title);
            this.voteStat = itemView.findViewById(R.id.card_vote);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickFavourites(view, getAdapterPosition(), favourites.get(getAdapterPosition()));
        }
    }
}