package com.example.jonas.tmdb_test_jonas_haag.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.jonas.tmdb_test_jonas_haag.DAO.FavouriteMovie;
import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.adapter.FavouritesAdapter;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.viewmodel.MovieViewModel;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity implements FavouritesAdapter.ItemClickListener{

    private Toolbar toolbar;
    private MovieViewModel movieViewModel;
    private FavouritesAdapter favouritesAdapter;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        toolbar = findViewById(R.id.toolbar_movie);
        toolbar.setTitle(Constant.FAVOURITES);
        setSupportActionBar(toolbar);

        movieViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.favourite_recyclerview_favourites);
        favouritesAdapter = new FavouritesAdapter(this);
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favouritesAdapter.setClickListener(this);

        getMovieFavourites();
        removeFavouriteSwipe(recyclerView); //Swipe Delete to remove item in favourite list
    }

    void getMovieFavourites(){
        movieViewModel.getAllFavourites().observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovie> movies) {
                favouritesAdapter.getFavourites(movies);
            }
        });
    }

    @Override
    public void onItemClickFavourites(View view, int position, FavouriteMovie movie) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.MOVIE_ID, movie.getMovieId());
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    void removeFavouriteSwipe(RecyclerView recyclerView){
        final ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(Canvas c,
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                drawButtons(c, viewHolder);
            }
            private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
                float buttonWidthWithoutPadding = Constant.BUTTON_WIDTH_DELETE_DRAW - 20;
                float corners = 0;

                View itemView = viewHolder.itemView;
                Paint p = new Paint();

                RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                p.setColor(Color.RED);
                c.drawRoundRect(rightButton, corners, corners, p);
                drawText(getResources().getString(R.string.delete), c, rightButton, p);
            }

            private void drawText(String text, Canvas c, RectF button, Paint p) {
                float textSize = 60;
                p.setColor(Color.WHITE);
                p.setAntiAlias(true);
                p.setTextSize(textSize);

                float textWidth = p.measureText(text);
                c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    FavouriteMovie movie = favouritesAdapter.getWordAtPosition(position);
                    movieViewModel.deleteMovie(movie);
                    favouritesAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        };
        itemTouchHelper = new ItemTouchHelper(helper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
