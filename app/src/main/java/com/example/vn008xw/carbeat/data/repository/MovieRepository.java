package com.example.vn008xw.carbeat.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.NetworkBoundLocalResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.RateLimiter;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.LinkedHashMap;
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


  private RateLimiter<String> moviesListRateLimit = new RateLimiter<>(30, TimeUnit.MINUTES);

  @Inject
  public MovieRepository(AppExecutors appExecutors,
                         RxSharedPreferences rxSharedPreferences,
                         MovieService movieService) {
    this.appExecutors = appExecutors;
    this.rxSharedPreferences = rxSharedPreferences;
    this.movieService = movieService;
  }

  public LiveData<Resource<SearchResult>> searchMovies(@NonNull int page, @NonNull String year) {

    return new NetworkBoundLocalResource<SearchResult, SearchResult>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull SearchResult item) {
        final Map<Integer, SearchResult> localCache;
        if (cache.getValue() == null) {
          localCache = new LinkedHashMap<>();
          localCache.put(page, item);
        }else {
          localCache = cache.getValue();
          localCache.put(page, item);
        }

        appExecutors.mainThread().execute(()->cache.setValue(localCache));
        cacheIsDirty = false;
      }

      @NonNull
      @Override
      protected LiveData<SearchResult> loadFromLocalSource() {
        final MediatorLiveData<SearchResult> result = new MediatorLiveData<>();
        if (cache != null && cache.getValue() != null && cache.getValue().containsKey(page)) {
          result.setValue(cache.getValue().get(page));
        }else {
          result.setValue(null);
        }
        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable SearchResult data) {
        return data == null || cacheIsDirty || data.getPage() < page;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<SearchResult>> createCall() {
        Log.d(TAG, "Making the network call");
        return movieService.discoverByYear(BuildConfig.API_KEY, year);
      }
    }.asLiveData();
  }

  public void setRefresh() {
    cacheIsDirty = true;
  }
}
