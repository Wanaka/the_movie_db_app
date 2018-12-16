package com.example.jonas.tmdb_test_jonas_haag.activity.helper;

import com.example.jonas.tmdb_test_jonas_haag.model.Genre;

import java.util.List;

public class Helper {

    public Helper(){}

    public static String genres(List<Genre> genres){
        List<Genre> genreList = genres;
        StringBuilder builder = new StringBuilder();
        for(Genre genre:  genreList){
            builder.append(genre.getName() + " | ");
        }
        return builder.toString();
    }
}
