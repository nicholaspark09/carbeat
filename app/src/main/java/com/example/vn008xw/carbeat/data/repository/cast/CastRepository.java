package com.example.vn008xw.carbeat.data.repository.cast;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.Cast;
import com.example.vn008xw.carbeat.data.vo.MovieCastResult;
import com.example.vn008xw.carbeat.data.vo.NetworkBoundLocalResource;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 *  Repository that fetches and saves cast
 */
@ApplicationScope
public class CastRepository implements CastDataSourceContract {

  @NonNull private static final String TAG = CastRepository.class.getSimpleName();

  @VisibleForTesting
  final MovieService movieService;
  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  boolean cacheIsDirty = false;
  @VisibleForTesting
  int movieId = -1;
  @VisibleForTesting
  final MediatorLiveData<Map<Integer, MovieCastResult>> cache = new MediatorLiveData<>();

  @Inject
  public CastRepository(@NonNull MovieService movieService,
                        @NonNull AppExecutors appExecutors) {
    this.movieService = movieService;
    this.appExecutors = appExecutors;
  }

  @Override
  public LiveData<Resource<Cast>> getCast(@NonNull int castId) {
    return null;
  }

  @Override
  public LiveData<Resource<List<Cast>>> getMovieCast(@NonNull int theMovieId) {
    return new NetworkBoundLocalResource<List<Cast>, MovieCastResult>(appExecutors) {
      @Override
      protected void saveLocalSource(@NonNull MovieCastResult item) {
        final Map<Integer, MovieCastResult> localCache;
        localCache = cache.getValue() != null ? cache.getValue() : new LinkedHashMap<>();
        localCache.put(theMovieId, item);
        appExecutors.mainThread().execute(()-> cache.setValue(localCache));
        cacheIsDirty = false;
      }

      @NonNull
      @Override
      protected LiveData<List<Cast>> loadFromLocalSource() {
        Log.d("CastRepo", "Trying to load from local source");
        final MutableLiveData<List<Cast>> cast = new MutableLiveData<>();
        if (cache.getValue() != null && cache.getValue().containsKey(theMovieId)) {
          cast.setValue(cache.getValue().get(theMovieId).getCast());
        }else {
          cast.setValue(null);
        }
        return cast;
      }

      @Override
      protected boolean shouldFetch(@Nullable List<Cast> data) {
        return data == null || cacheIsDirty;
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<MovieCastResult>> createCall() {
        return movieService.getCredits(theMovieId, BuildConfig.API_KEY);
      }
    }.asLiveData();
  }

  @Override
  public void saveCast(@NonNull Cast cast) {

  }

  @Override
  public void deleteCast(@NonNull Cast cast) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public void refresh() {
    cacheIsDirty = true;
  }
}
