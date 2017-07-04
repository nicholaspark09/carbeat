package com.example.vn008xw.carbeat.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.db.FavoriteMovieDao;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 7/2/17.
 */
@ApplicationScope
public class FavoriteMovieRepository {

  @NonNull
  private static final String TAG = FavoriteMovieRepository.class.getSimpleName();

  @VisibleForTesting
  final AppExecutors appExecutors;

  @VisibleForTesting
  final MovieService movieService;

  @VisibleForTesting
  final FavoriteMovieDao favoriteMovieDao;

  @Inject
  public FavoriteMovieRepository(AppExecutors appExecutors,
                                 MovieService movieService,
                                 FavoriteMovieDao favoriteMovieDao) {
    this.appExecutors = appExecutors;
    this.movieService = movieService;
    this.favoriteMovieDao = favoriteMovieDao;
  }

  public LiveData<Resource<List<FavoriteMovie>>> loadFavoriteMovies() {
    final MediatorLiveData<Resource<List<FavoriteMovie>>> result = new MediatorLiveData<>();
    final LiveData<List<FavoriteMovie>> localSource = favoriteMovieDao.loadFavoriteMovies();
    appExecutors.mainThread().execute(() -> {
      result.addSource(localSource, data -> {
        result.removeSource(localSource);
        if (data == null) {
          result.setValue(Resource.error("No favorites", null));
        } else {
          result.setValue(Resource.success(data));
        }
      });
    });
    return result;
  }

  public LiveData<Resource<Movie>> loadFavoriteMovie(int id) {
    final LiveData<FavoriteMovie> localSource = favoriteMovieDao.loadMovie(id);
    final MediatorLiveData<Resource<Movie>> result = new MediatorLiveData<>();
    appExecutors.mainThread().execute(() -> {
      result.addSource(localSource, data -> {
        result.removeSource(localSource);
        if (data == null) {
          result.setValue(Resource.error("Couldn't find your favorite movie", null));
        } else {
          result.setValue(Resource.success(data.getMovie()));
        }
      });
    });
    return result;
  }
}
