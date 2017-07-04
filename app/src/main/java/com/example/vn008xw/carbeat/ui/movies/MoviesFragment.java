package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.MainActivity;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Poster;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentMoviesBinding;
import com.example.vn008xw.carbeat.ui.NavigationController;
import com.example.vn008xw.carbeat.ui.movie.MovieImageListAdapter;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;
import com.example.vn008xw.carbeat.utils.MoviesUtilKt;

import org.jetbrains.annotations.NotNull;

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

  AutoClearedValue<MoviesListAdapter> mMoviesAdapter;
  AutoClearedValue<FragmentMoviesBinding> binding;
  private MoviesViewModel moviesViewModel;
  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @Inject
  NavigationController navigationController;
  RecyclerView.OnScrollListener scrollListener;


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
    final MoviesListAdapter adapter = new MoviesListAdapter((movie, imageView)->{
      moviesViewModel.saveMovie(movie);
      navigationController.navigateToMovie((MainActivity)getActivity(), movie.getId(), imageView);
    });
    this.mMoviesAdapter = new AutoClearedValue<>(this, adapter);
    binding.get().recyclerView.setAdapter(adapter);

    moviesViewModel.offset.postValue(1);
    moviesViewModel.getMovies().observe(this, listResource -> {

      if (listResource != null) {
        if (listResource.status == Status.ERROR) {
          Toast.makeText(getContext(), listResource.message, Toast.LENGTH_SHORT)
                  .show();
        } else {
          final List<Movie> movies = MoviesUtilKt.extractMovies(listResource);
          this.mMoviesAdapter.get().replace(movies);
        }
        setLoading(listResource.status);
      }
    });
    initSwipeListener();
    initScrollBottom();
  }



  private void initSwipeListener() {
    binding.get().swipeLayout.setOnRefreshListener(() -> {
      this.mMoviesAdapter.get().replace(null);
      moviesViewModel.refreshAndReload();
    });
  }

  private void initScrollBottom() {
    scrollListener = new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager)
                recyclerView.getLayoutManager();
        int lastPosition = layoutManager
                .findLastVisibleItemPosition();
        if (lastPosition == mMoviesAdapter.get().getItemCount() - 1) {
          moviesViewModel.loadMore();
        }
      }
    };
    binding.get().recyclerView.addOnScrollListener(scrollListener);
  }

  private void setLoading(Status status) {
    final boolean isLoading = status == Status.LOADING ? true : false;
      binding.get().swipeLayout.setRefreshing(isLoading);
      binding.get().swipeLayout.setEnabled(!isLoading);
      binding.get().setLoading(isLoading);
  }


  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
