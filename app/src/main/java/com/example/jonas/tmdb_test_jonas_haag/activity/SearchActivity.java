package com.example.jonas.tmdb_test_jonas_haag.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jonas.tmdb_test_jonas_haag.R;
import com.example.jonas.tmdb_test_jonas_haag.adapter.MoviesAdapter;
import com.example.jonas.tmdb_test_jonas_haag.constant.Constant;
import com.example.jonas.tmdb_test_jonas_haag.model.Result;
import com.example.jonas.tmdb_test_jonas_haag.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements MoviesAdapter.ItemClickListener, View.OnClickListener{

    private Toolbar toolbar;
    private FloatingActionButton searchButton;
    private EditText inputText;
    private MovieViewModel movieViewModel;
    MoviesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle(Constant.EMPTY_PLACEHOLDER);
        setSupportActionBar(toolbar);
        searchButton = findViewById(R.id.fab_search);
        searchButton.setOnClickListener(this);
        inputText = findViewById(R.id.edittext_search);

        movieViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview_movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(getApplicationContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        defaultSearch();
    }

    void defaultSearch(){
        movieViewModel.getMovieList(Constant.DEFAULT_SEARCH_WORD).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                adapter.getMovies(results);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab_search) {
            if(!inputText.getText().toString().isEmpty()) {
                hideKeyboard(view);
                movieViewModel.getMovieList(inputText.getText().toString()).observe(this, new Observer<List<Result>>() {
                    @Override
                    public void onChanged(@Nullable List<Result> results) {
                        adapter.getMovies(results);
                    }
                });
            }
            else{
                Toast.makeText(this, R.string.toast_search_without_text, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(View view, int position, Result movie) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.MOVIE_ID, movie.getId().toString());
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}

