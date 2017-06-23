package com.example.vn008xw.carbeat.data.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.ApiResponse;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.SearchResult;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.RateLimiter;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Response;

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

  Map<Integer, SearchResult> cache;


  private RateLimiter<String> moviesListRateLimit = new RateLimiter<>(30, TimeUnit.MINUTES);

  @Inject
  public MovieRepository(AppExecutors appExecutors,
                         RxSharedPreferences rxSharedPreferences,
                         MovieService movieService) {
    this.appExecutors = appExecutors;
    this.rxSharedPreferences = rxSharedPreferences;
    this.movieService = movieService;
  }

  public LiveData<Resource<SearchResult>> searchMovies(@NonNull int offset, @NonNull String year) {
    final MediatorLiveData<Resource<SearchResult>> result = new MediatorLiveData<>();
    if (cache == null) {
      cache = new LinkedHashMap<>();
    }
      result.postValue(Resource.loading(null));
      appExecutors.networkIO().execute(()-> {
        Log.d(TAG, "Making the call on the network thread");
        LiveData<ApiResponse<SearchResult>> apiResponse = createNetworkCall(year);
        appExecutors.mainThread().execute(() -> {
          result.addSource(apiResponse, data -> {
            Log.d(TAG, "The data was: " + data.toString());
            result.removeSource(apiResponse);
            Log.d(TAG, "The call came back");
            if (data.isSuccessful()) {
              Log.d(TAG, "The call was successful");
              result.postValue(Resource.success(processResponse(data)));
            } else {

              Log.d(TAG, "The call wasn't a success");
              result.postValue(Resource.error(data.getErrorMessage(), processResponse(data)));
            }
          });
        });
      });

    return result;
  }

  protected SearchResult processResponse(ApiResponse<SearchResult> response) {
    return response.getBody();
  }

  public LiveData<ApiResponse<SearchResult>> createNetworkCall(String year) {
      return movieService.discoverByYear(BuildConfig.API_KEY, year);
  }
}
