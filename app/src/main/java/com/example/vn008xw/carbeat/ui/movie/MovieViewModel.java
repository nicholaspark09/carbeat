package com.example.vn008xw.carbeat.ui.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Pair;

import com.example.vn008xw.carbeat.data.repository.FavoriteMovieRepository;
import com.example.vn008xw.carbeat.data.repository.ImageRepository;
import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;

import javax.inject.Inject;

/**
 * Created by vn008xw on 7/11/17.
 */

public class MovieViewModel extends ViewModel {

  @VisibleForTesting
  final MovieRepository movieRepository;
  @VisibleForTesting
  final ImageRepository imageRepository;
  @VisibleForTesting
  final FavoriteMovieRepository favoriteRepository;
  @VisibleForTesting
  final MutableLiveData<Integer> movieId;
  @VisibleForTesting
  final LiveData<Resource<Movie>> movieResult;
  @VisibleForTesting
  final LiveData<Resource<FavoriteMovie>> favoriteMovie;
  @VisibleForTesting
  final LiveData<Resource<ImageResult>> images;
  @VisibleForTesting
  final MutableLiveData<Pair<Integer, Boolean>> saved;

  @SuppressWarnings("unchecked")
  @Inject
  public MovieViewModel(MovieRepository movieRepository,
                        ImageRepository imageRepository,
                        FavoriteMovieRepository favoriteMovieRepository) {
    this.movieRepository = movieRepository;
    this.imageRepository = imageRepository;
    favoriteRepository = favoriteMovieRepository;

    movieId = new MutableLiveData<>();
    saved = new MutableLiveData<>();

    movieResult = Transformations.switchMap(movieId, id->{
      if (id == null) return AbsentLiveData.create();
      return movieRepository.loadMovie(id);
    });

    favoriteMovie = Transformations.switchMap(saved, pair->{
      if (pair == null) return AbsentLiveData.create();
      return favoriteMovieRepository.loadFavoriteMovie(pair.first);
    });

    images = Transformations.switchMap(movieId, id->{
      if (id == null) return AbsentLiveData.create();
      return imageRepository.loadImages(id);
    });
  }

  void setFind(@NonNull Integer id, @NonNull Boolean find) {
    saved.setValue(Pair.create(id, find));
  }

  void setMovieId(@NonNull Integer id) {
    movieId.setValue(id);
  }

  LiveData<Resource<Movie>> getMovie() {
    return movieResult;
  }

  LiveData<Resource<ImageResult>> getImages() {
    return images;
  }

  LiveData<Resource<FavoriteMovie>> getFavoriteMovie() {
    return favoriteMovie;
  }

  void saveMovie(@NonNull Movie movie) {
    final FavoriteMovie favoriteMovie = new FavoriteMovie(movie.getTitle(), movie);
    favoriteRepository.saveFavoriteMovie(favoriteMovie);
    saved.setValue(Pair.create(movie.getId(), true));
  }
}
