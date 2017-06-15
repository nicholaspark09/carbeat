package com.example.vn008xw.carbeat.utils;

import android.arch.lifecycle.LiveData;

import com.example.vn008xw.carbeat.data.vo.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter that converts a retrofit Call into a LiveData of ApiResponse
 */

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

  private final Type responseType;

  public LiveDataCallAdapter(Type responseType) {
    this.responseType = responseType;
  }

  @Override
  public Type responseType() {
    return responseType;
  }

  @Override
  public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
    return new LiveData<ApiResponse<R>>() {
      // An atomic boolean is thread safe
      // Use it when there are multiple threads that need access
      AtomicBoolean started = new AtomicBoolean(false);

      @Override
      protected void onActive() {
        super.onActive();
        if (started.compareAndSet(false, true)) {
          call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
              postValue(new ApiResponse<>(response));
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
              postValue(new ApiResponse<R>(t));
            }
          });
        }
      }
    };
  }
}
