package com.example.jonas.tmdb_test_jonas_haag.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.Genre;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieDetails;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;
import com.example.jonas.tmdb_test_jonas_haag.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MovieViewModel movieViewModel;
    private TextView detailTitle, detailOverview, detailRating, detailGenre, detailRuntime, detailReleaseDate;
    private ImageView detailImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.toolbar_movie);




        detailTitle = findViewById(R.id.detail_title);
        detailOverview = findViewById(R.id.detail_overview);
        detailRating = findViewById(R.id.detail_rating);
        detailImg = findViewById(R.id.detail_image);
        detailGenre = findViewById(R.id.detail_genre);
        detailRuntime = findViewById(R.id.detail_runtime);
        detailReleaseDate = findViewById(R.id.detail_release_date);

        movieViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);

        final String movieId = getIntent().getExtras().getString(Constant.MOVIE_ID);

        toolbar.setTitle(movieId);
        setSupportActionBar(toolbar);

        Toast.makeText(this, movieId, Toast.LENGTH_SHORT).show();


        movieViewModel.getMovieDetails(movieId).observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails movieDetails) {
                String imagePath = "https://image.tmdb.org/t/p/w500" + movieDetails.getPosterPath();
                Picasso.with(getApplicationContext()).load(imagePath).into(detailImg);
                detailTitle.setText(movieDetails.getTitle());
                detailOverview.setText(movieDetails.getOverview());
                detailRating.setText(movieDetails.getVoteAverage() + Constant.AVERAGE_VOTE_OF_10);
                detailRuntime.setText(movieDetails.getRuntime().toString() + "min");
                detailReleaseDate.setText("(" + movieDetails.getReleaseDate() + ")");

                //Create helper class
                List<Genre> genreList = movieDetails.getGenres();
                StringBuilder builder = new StringBuilder();
                for(Genre genre:  genreList){
                    builder.append(genre.getName() + " | ");
                }
                detailGenre.setText(builder.toString());
            }
        });


    }

}
