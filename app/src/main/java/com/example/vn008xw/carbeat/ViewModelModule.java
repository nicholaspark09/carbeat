package com.example.vn008xw.carbeat;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.vn008xw.carbeat.ui.movies.MoviesViewModel;
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
  abstract ViewModelProvider.Factory bindViewModelFactory(CarBeatViewModelFactory factory);
}