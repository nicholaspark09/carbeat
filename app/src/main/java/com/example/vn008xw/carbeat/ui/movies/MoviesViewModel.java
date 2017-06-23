package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.data.movie.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/19/17.
 */
public class MoviesViewModel extends ViewModel implements MoviesViewModelContract {

  @VisibleForTesting
  final MutableLiveData<Integer> offset = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<SearchResult>> searchResults;

  @SuppressWarnings("unchecked")
  @Inject
  public MoviesViewModel(MovieRepository movieRepository) {
    searchResults = Transformations.switchMap(offset,
            integer -> {
      if (integer == null) {
        return AbsentLiveData.create();
      }
      return  movieRepository.searchMovies(integer, "2017");
    });
  }

  public LiveData<Resource<SearchResult>> getMovies() {
    return searchResults;
  }

  public void setOffset(@NonNull int index) {
    if (offset.getValue() == index) return;
    offset.setValue(index);
  }

  @Override
  public void refreshAndReload() {
    offset.postValue(0);
  }
}
