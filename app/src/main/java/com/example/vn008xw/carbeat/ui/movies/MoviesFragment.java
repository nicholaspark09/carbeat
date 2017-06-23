package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Movie;
import static com.example.vn008xw.carbeat.data.vo.Status.*;

import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentMoviesBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;
import com.example.vn008xw.carbeat.utils.MoviesUtilKt;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/19/17.
 */
public class MoviesFragment extends BaseView {

  private static final String TAG = MoviesFragment.class.getSimpleName();

  public static MoviesFragment newInstance() {
    return new MoviesFragment();
  }

  AutoClearedValue<MoviesAdapter> mMoviesAdapter;
  AutoClearedValue<FragmentMoviesBinding> binding;
  private MoviesViewModel moviesViewModel;
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @Inject
  AppExecutors appExecutors;


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
            new MoviesAdapter(appExecutors);

    this.mMoviesAdapter = new AutoClearedValue<>(this, adapter);
    binding.get().recyclerView.setAdapter(adapter);
    moviesViewModel.getMovies().observe(this, listResource -> {


      if (listResource != null) {
        if (listResource.status == ERROR) {

          Toast.makeText(getContext(), listResource.message, Toast.LENGTH_SHORT)
                  .show();
        }else {

          final List<Movie> movies = MoviesUtilKt.extractMovies(listResource);
          this.mMoviesAdapter.get().replace(movies);
        }
        setLoading(listResource.status);
      }else {
        this.mMoviesAdapter.get().replace(Collections.emptyList());
      }
    });
    initSwipeListener();
  }

  private void initSwipeListener() {
    binding.get().swipeLayout.setOnRefreshListener(() -> {
      this.mMoviesAdapter.get().replace(Collections.emptyList());
      moviesViewModel.refreshAndReload();
    });
  }

  private void initScrollBottom() {
    binding.get().recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mMoviesAdapter.get().getItemCount() > 0) {

        }
      }
    });
  }

  private void setLoading(Status status) {
    final boolean isLoading = status == LOADING ? true : false;
      binding.get().swipeLayout.setRefreshing(isLoading);
      binding.get().swipeLayout.setEnabled(!isLoading);
      binding.get().setLoading(isLoading);
  }


  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
