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
import com.example.vn008xw.carbeat.data.vo.NetworkBoundLocalResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.ReviewResult;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/29/17.
 */
@ApplicationScope
public class ReviewRepository {

  @NonNull private static final String TAG = ReviewRepository.class.getSimpleName();

  @VisibleForTesting
  final MovieService movieService;

  @VisibleForTesting
  final AppExecutors appExecutors;

  @VisibleForTesting
  boolean cacheIsDirty = false;

  @VisibleForTesting
  int movieId;

  @VisibleForTesting
  int currentPage = 1;

  @VisibleForTesting
  final MediatorLiveData<Map<Integer, ReviewResult>> cache = new MediatorLiveData<>();

  @Inject
  public ReviewRepository(AppExecutors appExecutors,
                          MovieService movieService) {
    this.movieService = movieService;
    this.appExecutors = appExecutors;
  }

  public LiveData<Resource<List<ReviewResult>>> loadReviews(int movieId, int pageId) {

    currentPage = pageId;
    if (this.movieId != movieId) {
      cacheIsDirty = true;
      this.movieId = movieId;
    }
    return new NetworkBoundLocalResource<List<ReviewResult>, ReviewResult>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull ReviewResult item) {
        final Map<Integer, ReviewResult> localCache;
        if (cache.getValue() == null) {
          localCache = new LinkedHashMap<>();
        }else {
          localCache = cache.getValue();
        }
        localCache.put(pageId, item);
        appExecutors.mainThread().execute(()->{
          cache.setValue(localCache);
          cacheIsDirty = false;
        });
      }

      @NonNull
      @Override
      protected LiveData<List<ReviewResult>> loadFromLocalSource() {
        final MediatorLiveData<List<ReviewResult>> result = new MediatorLiveData<>();
        if (cache.getValue() != null && cache.getValue().size() > 0) {
          List<ReviewResult> cached = new ArrayList<>(cache.getValue().values());
          appExecutors.mainThread().execute(()->result.setValue(cached));
        }else {
          appExecutors.mainThread().execute(()->result.setValue(null));
        }
        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable List<ReviewResult> data) {
        return data == null || data.size() < currentPage || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<ReviewResult>> createCall() {
        return movieService.getReviews(movieId, BuildConfig.API_KEY, currentPage);
      }
    }.asLiveData();
  }
}
