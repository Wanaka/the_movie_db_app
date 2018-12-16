package com.example.jonas.tmdb_test_jonas_haag.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonas.tmdb_test_jonas_haag.DAO.FavouriteMovie;
import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.activity.helper.Helper;
import com.example.jonas.tmdb_test_jonas_haag.adapter.FavouritesAdapter;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieDetails;
import com.example.jonas.tmdb_test_jonas_haag.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private MovieViewModel movieViewModel;
    private TextView detailTitle, detailOverview, detailRating, detailGenre, detailRuntime, detailReleaseDate;
    private ImageView detailImg, detailSaveStar;
    private String movieId;
    private int getAverageVote;
    private Boolean checkIfIdSame, removeFavourite;
    private FavouriteMovie fm;

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
        detailSaveStar = findViewById(R.id.detail_star_favourite);
        detailSaveStar.setColorFilter(getResources().getColor(R.color.colorGrey));
        detailSaveStar.setOnClickListener(this);

        checkIfIdSame = Constant.FALSE;
        removeFavourite = Constant.FALSE;

        movieViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);

        movieId = getIntent().getExtras().getString(Constant.MOVIE_ID);

        toolbar.setTitle(Constant.DETAILS);
        setSupportActionBar(toolbar);

        movieViewModel.getMovieDetails(movieId).observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails movieDetails) {
                String imagePath = Constant.IMAGEPATH + movieDetails.getPosterPath();
                Picasso.with(getApplicationContext()).load(imagePath).into(detailImg);
                detailTitle.setText(movieDetails.getTitle());
                detailOverview.setText(movieDetails.getOverview());
                getAverageVote = movieDetails.getVoteAverage();
                detailRating.setText(getAverageVote + Constant.AVERAGE_VOTE_OF_10);
                detailRuntime.setText(movieDetails.getRuntime().toString() + Constant.MIN);
                detailReleaseDate.setText("(" + movieDetails.getReleaseDate() + ")");
                detailGenre.setText(Helper.genres(movieDetails.getGenres()));
            }
        });

        movieViewModel.getAllFavourites().observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovie> favouriteMovies) {

                //Check is favourite list is empty or not
                if(!favouriteMovies.isEmpty()) {
                    for (FavouriteMovie movie : favouriteMovies) {

                        //If the movie id from favourite list is equal to the movie details id: then show yellow star
                        if (movie.getMovieId().equals(movieId)) {
                            checkIfIdSame = Constant.TRUE;
                            detailSaveStar.setColorFilter(getResources().getColor(R.color.colorYellow));
                            removeFavourite = Constant.TRUE;
                            fm = movie;
                        } else {
                            checkIfIdSame = Constant.FALSE;
                            removeFavourite = Constant.FALSE;
                        }
                    }
                } else{
                    checkIfIdSame = Constant.FALSE;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        //Save movie to DB if favourite id and movie detail id are not equal
        if (checkIfIdSame == Constant.FALSE) {
            FavouriteMovie movie = new FavouriteMovie(movieId, detailTitle.getText().toString(), getAverageVote);
            movieViewModel.insert(movie);
            detailSaveStar.setColorFilter(getResources().getColor(R.color.colorYellow));
        }
        else if(removeFavourite) {
            movieViewModel.deleteMovie(fm);
            detailSaveStar.setColorFilter(getResources().getColor(R.color.colorGrey));
        }
    }
}
