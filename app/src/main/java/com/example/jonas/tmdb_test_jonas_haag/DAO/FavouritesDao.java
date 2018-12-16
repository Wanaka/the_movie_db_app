package com.example.jonas.tmdb_test_jonas_haag.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Query("SELECT * FROM favourite")
    LiveData<List<FavouriteMovie>> getAllFavourites();

    @Insert
    void insert(FavouriteMovie movie);

    @Delete
    void deleteWord(FavouriteMovie movie);
}
