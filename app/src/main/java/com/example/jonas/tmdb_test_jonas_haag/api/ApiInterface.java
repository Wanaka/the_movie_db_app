package com.example.jonas.tmdb_test_jonas_haag.api;


import com.example.jonas.tmdb_test_jonas_haag.model.BelongsToCollection;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieDetails;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/search/movie")
    Call<MovieList> getMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query
            );

    @GET("/3/movie/{id}?")
    Call<MovieDetails> getDetails(
            @Path("id") String id,
            @Query("api_key") String api_key,
            @Query("language") String language
            );

    //https://api.themoviedb.org/3/movie/557?api_key=bc41a44f3cc012cdc48cdba42f591b6c&language=en-US


}