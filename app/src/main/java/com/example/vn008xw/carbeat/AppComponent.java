package com.example.vn008xw.carbeat;

import com.example.vn008xw.carbeat.ui.account.AccountFragment;
import com.example.vn008xw.carbeat.ui.cast.CastActivity;
import com.example.vn008xw.carbeat.ui.cast.CastFragment;
import com.example.vn008xw.carbeat.ui.favorites.FavoritesFragment;
import com.example.vn008xw.carbeat.ui.movie.MovieActivity;
import com.example.vn008xw.carbeat.ui.movie.MovieFragment;
import com.example.vn008xw.carbeat.ui.movies.MoviesFragment;
import com.example.vn008xw.carbeat.ui.shows.ShowsFragment;

/**
 * Created by vn008xw on 6/6/17.
 */

public interface AppComponent {
  void inject(CarBeatApp carBeatApp);

  void inject(MainActivity mainActivity);

  void inject(MoviesFragment moviesFragment);

  void inject(MovieFragment movieFragment);

  void inject(MovieActivity movieActivity);

  void inject(FavoritesFragment favoritesFragment);

  void inject(CastActivity castActivity);

  void inject(CastFragment castFragment);

  void inject(AccountFragment accountFragment);

  void inject(ShowsFragment showsFragment);
}
