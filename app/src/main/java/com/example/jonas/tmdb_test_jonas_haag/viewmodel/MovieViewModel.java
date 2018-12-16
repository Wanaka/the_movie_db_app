package com.example.jonas.tmdb_test_jonas_haag.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.jonas.tmdb_test_jonas_haag.DAO.FavouriteMovie;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieDetails;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;
import com.example.jonas.tmdb_test_jonas_haag.repo.MovieRepo;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    MovieRepo repo;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repo = new MovieRepo(application);
    }

    public void insert(FavouriteMovie word) {
        repo.insert(word);
    }

    public void deleteMovie(FavouriteMovie movie) {repo.deleteMovie(movie);}

    public LiveData<List<FavouriteMovie>> getAllFavourites() {
        return repo.getAllFavourites();
    }

    public LiveData<List<Result>> getMovieList(String sendText) {
        return repo.getMovieList(sendText);
    }

    public LiveData<MovieDetails> getMovieDetails(String sendText) {
        return repo.getMovieDetails(sendText);
    }
}
