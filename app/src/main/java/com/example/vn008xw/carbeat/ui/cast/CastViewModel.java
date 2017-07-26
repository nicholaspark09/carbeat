package com.example.vn008xw.carbeat.ui.cast;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.repository.cast.CastRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Cast;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 7/19/17.
 */

public class CastViewModel extends ViewModel {

  @VisibleForTesting
  final CastRepository castRepository;
  @VisibleForTesting
  final MovieRepository movieRepository;
  @VisibleForTesting
  final LiveData<Resource<List<Cast>>> cast;
  @VisibleForTesting
  final MutableLiveData<Integer> movieId;

  @SuppressWarnings("unchecked")
  @Inject
  public CastViewModel(@NonNull CastRepository castRepository,
                       @NonNull MovieRepository movieRepository) {
    this.castRepository = castRepository;
    this.movieRepository = movieRepository;
    movieId = new MutableLiveData<>();

    cast = Transformations.switchMap(movieId, id -> {
      if (id == null) return AbsentLiveData.create();
      else return castRepository.getMovieCast(id);
    });
  }

  public void findMovie() {
    try {
      final Movie movie = movieRepository.getCachedMovie();
      movieId.setValue(movie.getId());
    }catch(IllegalArgumentException e) {
      Log.d("CastViewModel", "There was no saved movie");
    }
  }

  public LiveData<Resource<List<Cast>>> getCast() {
    return cast;
  }
}
