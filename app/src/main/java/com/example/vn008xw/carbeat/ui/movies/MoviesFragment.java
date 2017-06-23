package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentMoviesBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;
import com.example.vn008xw.carbeat.utils.MoviesUtilKt;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;

/**
 * Created by vn008xw on 6/19/17.
 */
public class MoviesFragment extends BaseView {

  public static MoviesFragment newInstance() {
    return new MoviesFragment();
  }

  AutoClearedValue<MoviesAdapter> mMoviesAdapter;
  AutoClearedValue<FragmentMoviesBinding> binding;
  private MoviesViewModel moviesViewModel;
  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    FragmentMoviesBinding fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false);
    binding = new AutoClearedValue<FragmentMoviesBinding>(this, fragmentMoviesBinding);
    return fragmentMoviesBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    moviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);
    moviesViewModel.offset.postValue(0);

    final MoviesAdapter adapter =
            new MoviesAdapter(Collections.emptyList());

    this.mMoviesAdapter = new AutoClearedValue<>(this, adapter);
    binding.get().recyclerView.setAdapter(mMoviesAdapter.get());
    moviesViewModel.getMovies().observe(this, listResource -> {


      if (listResource != null) {

        if (listResource.status == Status.LOADING) {
          binding.get().refreshButton.setEnabled(false);
          binding.get().setLoading(true);
        } else if (listResource.status == Status.ERROR) {
          binding.get().swipeLayout.setRefreshing(false);
          binding.get().setLoading(false);
          binding.get().refreshButton.setEnabled(true);
          Toast.makeText(getContext(), listResource.message, Toast.LENGTH_SHORT)
                  .show();
        }else {

            Log.d(MoviesFragment.class.getSimpleName(), "The data is: " + listResource.data.getResults());
            mMoviesAdapter.get().addMovies(MoviesUtilKt.extractMovies(listResource));

            binding.get().swipeLayout.setRefreshing(false);
            binding.get().setLoading(false);
            binding.get().refreshButton.setEnabled(true);
          }
      }
    });
    initSwipeListener();
  }

  private void initSwipeListener() {
    binding.get().swipeLayout.setOnRefreshListener(() -> moviesViewModel.refreshAndReload());
    binding.get().refreshButton.setOnClickListener(view -> moviesViewModel.refreshAndReload());
  }


  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
