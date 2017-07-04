package com.example.vn008xw.carbeat.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.SearchResult;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/19/17.
 */
public class MoviesViewModel extends ViewModel implements MoviesViewModelContract {

  @VisibleForTesting
  final MutableLiveData<Integer> offset = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<List<SearchResult>>> searchResults;
  @VisibleForTesting
  final MovieRepository movieRepository;

  @SuppressWarnings("unchecked")
  @Inject
  public MoviesViewModel(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
    searchResults = Transformations.switchMap(offset,
            integer -> {
      if (integer == null) {
        return AbsentLiveData.create();
      }
      return  movieRepository.loadMovies(integer, "2017");
    });
  }

  public LiveData<Resource<List<SearchResult>>> getMovies() {
    return searchResults;
  }

  @Override
  public void refreshAndReload() {
    movieRepository.setRefresh();
    offset.postValue(1);
  }

  public void saveMovie(@NonNull Movie movie) {
    movieRepository.saveMovie(movie);
  }

  @Override
  public void loadMore() {
    offset.postValue(offset.getValue()+1);
  }
}
