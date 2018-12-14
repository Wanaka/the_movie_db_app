package com.example.jonas.tmdb_test_jonas_haag.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.jonas.tmdb_test_jonas_haag.api.ApiInterface;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieDetails;
import com.example.jonas.tmdb_test_jonas_haag.model.MovieList;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepo {

    MutableLiveData<List<Result>> getMovieList = new MutableLiveData<>();
    MutableLiveData<MovieDetails> getDetails = new MutableLiveData<>();


    public MovieRepo(Application application) {
    }

    /* Get movie list */
    public LiveData<List<Result>> getMovieList(String sendText) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieList> call = apiInterface.getMovies(Constant.API_KEY, Constant.LANGUAGE, sendText);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MovieList responseBody = response.body();
                List<Result> moviesResult = responseBody.getResults();
                List<Result> movieList = new ArrayList<>();
                //Result movie = resultList.get(0);

                for(Result movie:  moviesResult){
                    Log.d("TAG", "onResponse:  HEY" + movie.getTitle());
                    movieList.add(movie);
                }
                getMovieList.setValue(movieList);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d("TAG", "onResponse:  FAILED CONNECTION");
            }
        });
        return getMovieList;
    }

    /* Get details of movie */
    public LiveData<MovieDetails> getMovieDetails(String sendText) {
        Log.d("TAG", "onResponse:  REPO SENDTEXT value: " + sendText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieDetails> call = apiInterface.getDetails(sendText, Constant.API_KEY, Constant.LANGUAGE);

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails moviesResult = response.body();
                Log.d("TAG", "onResponse:  REPO details" + moviesResult.getTitle());

                getDetails.setValue(moviesResult);
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.d("TAG", "onResponse: Details FAILED CONNECTION");
            }
        });
        return getDetails;
    }
}
