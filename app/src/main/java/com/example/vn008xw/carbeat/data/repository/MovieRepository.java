package com.example.vn008xw.carbeat.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.NetworkBoundLocalResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.RateLimiter;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Repository that handles movie objects
 */
@ApplicationScope
public class MovieRepository {

  @NonNull private static final String TAG = MovieRepository.class.getSimpleName();

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final RxSharedPreferences rxSharedPreferences;

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final MovieService movieService;

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  final AppExecutors appExecutors;

  @VisibleForTesting
  final MediatorLiveData<Map<Integer, SearchResult>> cache = new MediatorLiveData<>();

  @VisibleForTesting
  boolean cacheIsDirty = false;

  @VisibleForTesting
  Movie cachedMovie;

  int currentPage = 1;


  private RateLimiter<String> moviesListRateLimit = new RateLimiter<>(30, TimeUnit.MINUTES);

  @Inject
  public MovieRepository(AppExecutors appExecutors,
                         RxSharedPreferences rxSharedPreferences,
                         MovieService movieService) {
    this.appExecutors = appExecutors;
    this.rxSharedPreferences = rxSharedPreferences;
    this.movieService = movieService;
  }

  public LiveData<Resource<List<SearchResult>>> loadMovies(@NonNull int page, @NonNull String year) {
    currentPage = page;
    return new NetworkBoundLocalResource<List<SearchResult>, SearchResult>(appExecutors) {
      @Override
      protected void saveLocalSource(@NonNull SearchResult item) {
        final Map<Integer, SearchResult> localCache;
        if (cache.getValue() == null) {
          localCache = new LinkedHashMap<>();
        }else {
          localCache = cache.getValue();
        }
        localCache.put(page, item);
        appExecutors.mainThread().execute(()->cache.setValue(localCache));
        cacheIsDirty = false;
      }

      @NonNull
      @Override
      protected LiveData<List<SearchResult>> loadFromLocalSource() {
        final MediatorLiveData<List<SearchResult>> result = new MediatorLiveData<>();
        appExecutors.diskIO().execute(() -> {
          if (cache.getValue() != null && cache.getValue().size() > 0) {
            List<SearchResult> cached = new ArrayList<>(cache.getValue().values());
            appExecutors.mainThread().execute(()-> result.setValue(cached));
          } else {
            appExecutors.mainThread().execute(()->result.setValue(null));
          }
        });
        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable List<SearchResult> data) {
        return data == null || data.size() < currentPage || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<SearchResult>> createCall() {
        return movieService.discoverByYear(BuildConfig.API_KEY, currentPage, year);
      }
    }.asLiveData();
  }

  public LiveData<Resource<Movie>> loadMovie(@NonNull int movieId) {
    final MediatorLiveData<Resource<Movie>> result = new MediatorLiveData<>();
    if (cachedMovie != null) {
      result.setValue(Resource.success(cachedMovie));
    } else {
      // The locally cached items are not sorted by id so...dig
      if (cache.getValue() != null && cache.getValue().size() > 0) {
        appExecutors.diskIO().execute(() -> {
          for (SearchResult searchResult : cache.getValue().values()) {
            for (Movie movie : searchResult.getResults()) {
              if (movie.getId() == movieId) {
                // Save locally
                appExecutors.mainThread().execute(() -> {
                  saveMovie(movie);
                  result.setValue(Resource.success(movie));
                });
                break;
              }
            }
          }

        });
      } else {
        // Getting here indicates it was nowhere to be found in cache
        result.setValue(Resource.error("Sorry, the movie wasn't found", null));
      }
    }
    return result;
  }

  @MainThread
  public void saveMovie(@NonNull Movie movie) {
    this.cachedMovie = movie;
  }

  public void setRefresh() {
    cacheIsDirty = true;
  }
}
