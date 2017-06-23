package com.example.vn008xw.carbeat.data.vo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.example.vn008xw.carbeat.AppExecutors;

/**
 * Created by vn008xw on 6/21/17.
 */

public abstract class NetworkBoundLocalResource<ResultType, RequestType> {

  private final AppExecutors appExecutors;

  private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

  @MainThread
  public NetworkBoundLocalResource(AppExecutors appExecutors) {
    this.appExecutors = appExecutors;
  }

  @NonNull
  @MainThread
  protected abstract LiveData<ResultType> loadFromLocalSource();

  @MainThread
  protected abstract boolean shouldFetch(@Nullable ResultType data);

  @NonNull
  @MainThread
  protected abstract LiveData<ApiResponse<RequestType>> createCall();

  @WorkerThread
  protected RequestType processResponse(ApiResponse<RequestType> response) {
    return response.getBody();
  }

  protected void onFetchFailed(){}
}
