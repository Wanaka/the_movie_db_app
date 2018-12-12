package com.example.jonas.tmdb_test_jonas_haag.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.api.ApiInterface;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieList;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieList> call = apiInterface.geMovies(Constant.API_KEY, Constant.LANGUAGE, "Spiderman");

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MovieList movieList = response.body();
                List<Result> resultList = movieList.getResults();
                //Result movie = resultList.get(0);

                for(Result m:  resultList){
                    Log.d("TAG", "onResponse:  HEY" + m.getTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });

    }


    }

