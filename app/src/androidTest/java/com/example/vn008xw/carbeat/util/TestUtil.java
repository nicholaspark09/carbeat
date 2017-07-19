package com.example.vn008xw.carbeat.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.Movie;

/**
 * Created by vn008xw on 7/12/17.
 */

public class TestUtil {

  private TestUtil() {
    throw new AssertionError("No instances");
  }

  public static Movie createMovie(@NonNull int id,
                                  @NonNull String title,
                                  @Nullable String description) {
    final String overview = description == null ? "Overview" : description;
    return new Movie(id, title, "en", overview,
            title, "backdrop_path", 1f, 109, false,
            1f, "poster_path", "");
  }

  public static FavoriteMovie createFavoriteMovie(@NonNull Movie movie) {
    return new FavoriteMovie(movie.getTitle(), movie);
  }

}
