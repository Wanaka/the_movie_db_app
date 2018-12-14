package com.example.jonas.tmdb_test_jonas_haag.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Result> result;
    private LayoutInflater mInflater;
    Result text;
    Context c;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MoviesAdapter(Context context) {
        this.result = new ArrayList<>();
        c = context;
        this.mInflater = LayoutInflater.from(context);
    }


    public void getMovies(List<Result> results){
        result = results;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        text = result.get(position);
        holder.title.setText(text.getTitle());
        holder.voteStat.setText(text.getVoteAverage().toString() + Constant.AVERAGE_VOTE_OF_10);
        String imagePath = "https://image.tmdb.org/t/p/w500" + text.getPosterPath();
        Picasso.with(c).load(imagePath).into(holder.image);
        Log.d("TAG", "onResponse:  FROM ADAPTER  " + imagePath);

    }

    // total number of rows
    @Override
    public int getItemCount() {
            return result.size();
    }

    // convenience method for getting data at click position
    public Result getItem(int id) {
        return result.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events

    public interface ItemClickListener {
        void onItemClick(View view, int position, Result movie);
    }

    public Result getWordAtPosition (int position) {
        return result.get(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView voteStat;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.card_title);
            this.voteStat = itemView.findViewById(R.id.card_vote);
            this.image = itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), result.get(getAdapterPosition()));
        }
    }
}