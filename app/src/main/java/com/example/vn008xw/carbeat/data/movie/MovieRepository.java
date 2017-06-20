package com.example.vn008xw.carbeat.data.movie;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.db.MovieDao;
import com.example.vn008xw.carbeat.data.db.MovieDb;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.NetworkBoundResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.RateLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import static com.example.vn008xw.carbeat.utils.QueryUtilKt.addToMap;

/**
 * Created by vn008xw on 6/14/17.
 */
@ApplicationScope
public class MovieRepository {

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final MovieDb movieDb;

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final MovieDao movieDao;

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final MovieService movieService;

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final AppExecutors appExecutors;

  private RateLimiter<String> moviesListRateLimit = new RateLimiter<>(30, TimeUnit.MINUTES);

  HashMap<String, String> queryMap;

  @Inject
  public MovieRepository(AppExecutors appExecutors,
                         MovieDao movieDao,
                         MovieService movieService,
                         MovieDb movieDb,
                         @Named("CredentialsMap") HashMap<String, String> queryMap) {
    this.appExecutors = appExecutors;
    this.movieDao = movieDao;
    this.movieDb = movieDb;
    this.movieService = movieService;
    this.queryMap = queryMap;
  }

  public LiveData<Resource<List<Movie>>> loadMovies(@NonNull int offset, @NonNull String year) {

    return new NetworkBoundResource<List<Movie>, List<Movie>>(appExecutors) {

      @Override
      protected void saveCallResult(@NonNull List<Movie> item) {
        movieDao.insertMovies(item);
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Movie> data) {
        Log.d(MovieRepository.class.getSimpleName(), "Checking if we should fetch");
        return data == null || data.isEmpty();
      }

      @NonNull
      @Override
      protected LiveData<List<Movie>> loadFromDb() {
        return movieDao.loadMoviesByYear(year, offset);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<List<Movie>>> createCall() {
        Log.d("Repository", "Trying to call the movies from the service");
        HashMap<String, String> map = addToMap(queryMap, "y", year);
        Log.d(MovieRepository.class.getSimpleName(), "The Map is: " + map.toString());
        return movieService.query(map);
      }
    }.asLiveData();
  }
}
