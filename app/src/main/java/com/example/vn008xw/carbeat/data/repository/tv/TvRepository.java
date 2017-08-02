package com.example.vn008xw.carbeat.data.repository.tv;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.NetworkBoundLocalResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.Show;
import com.example.vn008xw.carbeat.data.vo.ShowResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

@ApplicationScope
public class TvRepository implements TvDataSource {

  @NonNull private static final String TAG = TvRepository.class.getSimpleName();

  @VisibleForTesting
  final MovieService api;
  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  final MutableLiveData<Map<Integer, ShowResult>> cache = new MutableLiveData<>();

  @VisibleForTesting
  boolean cacheIsDirty = false;
  @VisibleForTesting
  int currentPage = 1;

  @Inject
  public TvRepository(@NonNull MovieService api,
                      @NonNull AppExecutors appExecutors) {
    this.api = api;
    this.appExecutors = appExecutors;
  }

  @Override
  public LiveData<Resource<ShowResult>> getPopularShows(int page) {
    if (page != currentPage) {
      cacheIsDirty = true;
    }
    currentPage = page;

    return new NetworkBoundLocalResource<ShowResult, ShowResult>(appExecutors) {

      @Override
      protected void saveLocalSource(@NonNull ShowResult item) {
        final Map<Integer, ShowResult> localCache;
        if (cache.getValue() == null) localCache = new LinkedHashMap<>();
        else localCache = cache.getValue();

        localCache.put(currentPage, item);
        appExecutors.mainThread().execute(() -> {
          cache.setValue(localCache);
          cacheIsDirty = false;
        });
      }

      @NonNull
      @Override
      protected LiveData<ShowResult> loadFromLocalSource() {
        final MediatorLiveData<ShowResult> result = new MediatorLiveData<>();
        appExecutors.diskIO().execute(() -> {
          if (cache.getValue() != null && cache.getValue().containsKey(currentPage)) {
            appExecutors.mainThread().execute(() -> result.setValue(cache.getValue().get(currentPage)));
          } else {
            appExecutors.mainThread().execute(() -> result.setValue(null));
          }
        });
        return result;
      }

      @Override
      protected boolean shouldFetch(@Nullable ShowResult data) {
        return data == null || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<ShowResult>> createCall() {
        return api.getPopularTvShows(BuildConfig.API_KEY, currentPage);
      }
    }.asLiveData();
  }

  // TODO complete get show method
  @Override
  public LiveData<Resource<Show>> getShow(int showId) {
    return null;
  }

  @Override
  public void refresh() {
    cacheIsDirty = true;
  }
}
