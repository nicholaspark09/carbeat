package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.movie.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/19/17.
 */
@ApplicationScope
public class MoviesViewModel extends ViewModel {

  @VisibleForTesting
  final MutableLiveData<Integer> offset;
  @VisibleForTesting
  final LiveData<Resource<List<Movie>>> movies;

  @Inject
  public MoviesViewModel(MovieRepository movieRepository) {
    this.offset = new MutableLiveData<>();
    movies = Transformations.switchMap(offset,
            integer ->movieRepository.loadMovies(integer, "2017"));
  }

  public LiveData<Resource<List<Movie>>> getMovies() {
    return movies;
  }

}
