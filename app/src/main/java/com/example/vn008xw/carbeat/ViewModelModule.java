package com.example.vn008xw.carbeat;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.vn008xw.carbeat.ui.account.AccountViewModel;
import com.example.vn008xw.carbeat.ui.cast.CastViewModel;
import com.example.vn008xw.carbeat.ui.favorites.FavoritesViewModel;
import com.example.vn008xw.carbeat.ui.movie.MovieViewModel;
import com.example.vn008xw.carbeat.ui.movies.MoviesViewModel;
import com.example.vn008xw.carbeat.ui.review.ReviewViewModel;
import com.example.vn008xw.carbeat.utils.CarBeatViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by vn008xw on 6/22/17.
 */
@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(MoviesViewModel.class)
  abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MovieViewModel.class)
  abstract ViewModel bindMovieViewModel(MovieViewModel movieViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(ReviewViewModel.class)
  abstract ViewModel bindReviewViewModel(ReviewViewModel reviewViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(FavoritesViewModel.class)
  abstract ViewModel bindFavoritesViewModel(FavoritesViewModel favoritesViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(CastViewModel.class)
  abstract ViewModel bindCastViewModel(CastViewModel castViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(AccountViewModel.class)
  abstract ViewModel bindAccountViewModel(AccountViewModel accountViewModel);

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(CarBeatViewModelFactory factory);
}
