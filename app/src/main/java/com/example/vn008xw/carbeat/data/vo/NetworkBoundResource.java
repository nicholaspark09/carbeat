package com.example.vn008xw.carbeat.data.vo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;

/**
 * Created by vn008xw on 6/13/17.
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

  private final AppExecutors appExecutors;

  private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

  @MainThread
  public NetworkBoundResource(AppExecutors appExecutors) {
    this.appExecutors = appExecutors;
    LiveData<ResultType> dbSource = loadFromDb();
    result.setValue(Resource.loading(null));
    Log.d("BoundResource", "hello?");
    result.addSource(dbSource, data->{
      result.removeSource(dbSource);
      if (shouldFetch(data)) {
        fetchFromNetwork(dbSource);
      }else {
        result.addSource(dbSource, newData-> result.setValue(Resource.success(newData)));
      }
    });
  }

  public LiveData<Resource<ResultType>> asLiveData() {
    return result;
  }

  private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
    LiveData<ApiResponse<RequestType>> apiResponse = createCall();
    //add dbsource as a source
    result.addSource(dbSource, newData-> result.setValue(Resource.loading(newData)));
    result.addSource(apiResponse, response->{
      result.removeSource(apiResponse);
      result.removeSource(dbSource);
      if (response.isSuccessful()) {
        appExecutors.diskIO().execute(()->{
          saveCallResult(processResponse(response));
          appExecutors.mainThread().execute(()->{
            result.addSource(loadFromDb(),
                    newData-> result.setValue(Resource.success(newData)));
          });
        });
      }else {
        onFetchFailed();
        result.addSource(dbSource,
                newData-> result.setValue(Resource.error(response.getErrorMessage(), newData)));
      }
    });
  }

  protected void onFetchFailed(){}



  @WorkerThread
  protected RequestType processResponse(ApiResponse<RequestType> response) {
    return response.getBody();
  }

  @WorkerThread
  protected abstract void saveCallResult(@NonNull RequestType item);

  @MainThread
  protected abstract boolean shouldFetch(@Nullable ResultType data);

  @NonNull
  @MainThread
  protected abstract LiveData<ResultType> loadFromDb();

  @NonNull
  @MainThread
  protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
