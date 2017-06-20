package com.example.vn008xw.carbeat.ui.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.databinding.FragmentMoviesBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/19/17.
 */
public class MoviesFragment extends BaseView {

  public static MoviesFragment newInstance() {
    return new MoviesFragment();
  }

  @Inject
  MoviesViewModel moviesViewModel;

  AutoClearedValue<MoviesAdapter> mMoviesAdapter;
  AutoClearedValue<FragmentMoviesBinding> binding;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    FragmentMoviesBinding fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false);
    binding = new AutoClearedValue<>(this, fragmentMoviesBinding);
    return fragmentMoviesBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    moviesViewModel.offset.postValue(0);
    MoviesAdapter adapter =
            new MoviesAdapter(Collections.emptyList());
    this.mMoviesAdapter = new AutoClearedValue<>(this, adapter);

    binding.get().setLoading(true);
    moviesViewModel.getMovies().observe(this, listResource -> {
      if (listResource != null && listResource.data != null) {
        mMoviesAdapter.get().addMovies(listResource.data);
      }
      binding.get().setLoading(false);
    });
  }


  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
