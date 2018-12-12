package com.example.jonas.tmdb_test_jonas_haag.api;


import com.example.jonas.tmdb_test_jonas_haag.model.MovieList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/search/movie")
    Call<MovieList> geMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query
            );



    // https://api.themoviedb.org/3/search/movie?api_key=bc41a44f3cc012cdc48cdba42f591b6c&language=en-US&query=Venom&page=1&include_adult=false
}