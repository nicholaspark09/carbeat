package com.example.vn008xw.carbeat.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.Movie;

/**
 * Created by vn008xw on 6/14/17.
 */
@Database(entities = {Movie.class, FavoriteMovie.class}, version = 1)
public abstract class MovieDb extends RoomDatabase {

  abstract public MovieDao movieDao();

  abstract public FavoriteMovieDao favoriteMovieDao();
}
