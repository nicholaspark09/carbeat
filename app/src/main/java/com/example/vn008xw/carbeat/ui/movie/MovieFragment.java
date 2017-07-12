package com.example.vn008xw.carbeat.ui.movie;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentMovieBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;
import com.example.vn008xw.carbeat.utils.ImageUtilKt;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import view.FragmentDataBindingComponent;

import static com.example.vn008xw.carbeat.utils.DrawableUtilCompat.getDrawable;

/**
 * Created by vn008xw on 7/11/17.
 */

public class MovieFragment extends BaseView {

  @VisibleForTesting
  AutoClearedValue<FragmentMovieBinding> binding;
  @VisibleForTesting
  AutoClearedValue<MovieImageListAdapter> movieImageAdapter;
  @VisibleForTesting
  int movieId;
  @VisibleForTesting
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @VisibleForTesting
  MovieViewModel viewModel;

  @VisibleForTesting
  DataBindingComponent bindingComponent = new FragmentDataBindingComponent(this);
  private static final String MOVIE_ID = "MOVIE_ID";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      movieId = getArguments().getInt(MOVIE_ID, -1);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final FragmentMovieBinding mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false, bindingComponent);
    mBinding.setSaved(false);
    setHasOptionsMenu(true);
    binding = new AutoClearedValue<>(this, mBinding);
    return mBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);
    viewModel.getMovie().observe(this, observer -> {
      if (observer.status == Status.SUCCESS) {
        if (binding.get() != null) {
          binding.get().setMovie(observer.data);
          if (observer.data.getPosterPath() != null) {
            binding.get().setImageUrl(ImageUtilKt.getLargeImageUrl(observer.data.getPosterPath()));
          }
          viewModel.setFind(observer.data.getId(), true);
        }
      } else if (observer.status == Status.ERROR) {
        Toast.makeText(getContext(), observer.message, Toast.LENGTH_LONG).show();
      }
      binding.get().setLoading(observer.status != Status.LOADING);
    });

    final MovieImageListAdapter imageAdapter = new MovieImageListAdapter(new MovieImageListAdapter.MovieImageCallback() {
      @Override
      public void onClick(@NotNull Poster poster) {

      }
    });
    binding.get().imageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    binding.get().imageRecyclerView.setAdapter(imageAdapter);
    movieImageAdapter = new AutoClearedValue<>(this, imageAdapter);

    viewModel.getImages().observe(this, o -> {
      if (o.status == Status.SUCCESS) {
        movieImageAdapter.get().replace(o.data.getPosters());
      } else {
        Log.e("Image Exception", o.message);
      }
    });

    viewModel.getFavoriteMovie().observe(this, o -> {
      if (o.status == Status.SUCCESS) {
        binding.get().fab.setImageDrawable(getDrawable(getContext(), R.drawable.ic_star_white));
        binding.get().setSaved(true);
      } else {
        binding.get().fab.setImageDrawable(getDrawable(getContext(), R.drawable.ic_star_black));
        binding.get().setSaved(false);
      }
    });

    viewModel.setMovieId(movieId);
    setupToolbar();
    setupFabListener();
  }

  private void setupFabListener() {
    binding.get().fab.setOnClickListener((v) -> {
      if (binding.get().getSaved()) {
        // TODO complete the delete from favorites
      } else {
        if (binding.get().getMovie() != null) {
          viewModel.saveMovie(binding.get().getMovie());
        }
      }
    });
  }

  private void setupToolbar() {
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(binding.get().toolbar);
    final ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowHomeEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }

  public static MovieFragment newInstance(int movieId) {
    final MovieFragment fragment = new MovieFragment();
    final Bundle args = new Bundle();
    args.putInt(MOVIE_ID, movieId);
    fragment.setArguments(args);
    return fragment;
  }
}
