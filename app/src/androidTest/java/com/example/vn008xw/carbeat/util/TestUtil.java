package com.example.vn008xw.carbeat.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;

import com.example.vn008xw.carbeat.data.vo.Cast;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vn008xw on 7/12/17.
 */

public class TestUtil {

  private TestUtil() {
    throw new AssertionError("No instances");
  }

  public static Movie createMovie(int id,
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

  public static Cast createCast(int id,
                                @NonNull String name,
                                @NonNull String department) {
    return new Cast(id, name, 2,
            department, 1, "", id, name, 1, "");
  }

  public static ImmutableList<Cast> createCastList(int size) {
    String[] names = new String[]{"jack", "bob", "hans", "Chris"};
    List<Cast> cast = new ArrayList<>();
    for(int i = 0; i< size; ++i) {
      cast.add(createCast(i+1, names[i%3], ""));
    }
    return ImmutableList.copyOf(cast);
  }
}
