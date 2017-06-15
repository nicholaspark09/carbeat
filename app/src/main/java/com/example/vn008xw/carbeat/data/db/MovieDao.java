package com.example.vn008xw.carbeat.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.vn008xw.carbeat.data.vo.Movie;

import java.util.List;

/**
 * Created by vn008xw on 6/13/17.
 */
@Dao
public abstract class MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertMovies(List<Movie> movies);

  @Query("SELECT * FROM movie WHERE year = :year ORDER BY title LIMIT :offset, 10")
  public abstract LiveData<List<Movie>> loadMoviesByYear(String year,
                                                         int offset);

  @Query("SELECT * FROM movie WHERE imdbId = :id LIMIT 1")
  public abstract LiveData<Movie> loadMovie(String id);
}
