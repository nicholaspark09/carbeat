package com.example.vn008xw.carbeat.ui.movies;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.databinding.ListItemMovieBinding;
import com.example.vn008xw.carbeat.utils.ImageUtil;
import com.example.vn008xw.carbeat.view.DataBoundAdapter;

/**
 * Created by vn008xw on 6/25/17.
 */

public class MoviesListAdapter extends DataBoundAdapter<Movie, ListItemMovieBinding> {

  private final MovieClickCallback movieClickCallback;

  public MoviesListAdapter(MovieClickCallback movieClickCallback) {
    this.movieClickCallback = movieClickCallback;
  }

  @Override
  protected ListItemMovieBinding createBinding(ViewGroup parent) {
    ListItemMovieBinding binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()));

    binding.getRoot().setOnClickListener(v -> {
      final Movie movie = binding.getMovie();
      if (movie != null && movieClickCallback != null) {
        movieClickCallback.onClick(movie, binding.imageView);
      }
    });
    return binding;
  }

  @Override
  protected void bind(ListItemMovieBinding binding, Movie item) {
    binding.setMovie(item);
    if (ImageUtil.getLittleImage(item.getPosterPath()) != null)
      binding.setImageName(ImageUtil.getLittleImage(item.getPosterPath()));
  }

  @Override
  protected boolean areItemsTheSame(Movie oldItem, Movie newItem) {
    return oldItem.getId() == newItem.getId() && oldItem.equals(newItem);
  }

  @Override
  protected boolean areContentsTheSame(Movie oldItem, Movie newItem) {
    return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.equals(newItem);
  }

  public interface MovieClickCallback {
    void onClick(Movie movie, ImageView imageView);
  }
}
