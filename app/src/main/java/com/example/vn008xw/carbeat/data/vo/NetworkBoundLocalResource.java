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
 * Created by vn008xw on 6/21/17.
 */

public abstract class NetworkBoundLocalResource<ResultType, RequestType> {

  private final AppExecutors appExecutors;

  private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

  @MainThread
  public NetworkBoundLocalResource(AppExecutors appExecutors) {
    this.appExecutors = appExecutors;
    LiveData<ResultType> localSource = loadFromLocalSource();
    result.addSource(localSource, data->{

      Log.d("NetworkBound", "You got somethingback from local source");

      result.removeSource(localSource);

      if (shouldFetch(data)) {
        Log.d("NetworkBound", "Should be trying to make the network call");
        fetchFromNetwork(localSource);

      }else {
        Log.d("NetworkBound", "Apparently you don't need the network call...");
        // You don't need to hit the network again, so load it from cache
       result.addSource(localSource, oldData-> result.setValue(Resource.success(oldData)));
      }
    });
  }

  private void fetchFromNetwork(final LiveData<ResultType> localSource) {
    LiveData<ApiResponse<RequestType>> apiResponse = createCall();
    Log.d("Networkbound", "Trying to fetch from the network");
    result.addSource(localSource, oldData-> result.setValue(Resource.loading(oldData)));
    result.addSource(apiResponse, newData->{
      result.removeSource(apiResponse);
      result.removeSource(localSource);

      Log.d("Networkbound", "Got data back");
      if (newData.isSuccessful()) {

        Log.d("Networkbound", "Successfully received data from the network");
        appExecutors.diskIO().execute(()->{

          saveLocalSource(processResponse(newData));

          appExecutors.mainThread().execute(()->{

            result.addSource(loadFromLocalSource(),
                    freshData->{
                        result.setValue(Resource.success(freshData));
                    });

          });

        });
      }else {
        Log.d("Networkbound", "Got an error " + newData.getErrorMessage());
        onFetchFailed();
        result.addSource(localSource, data->{
          result.setValue(Resource.error(newData.getErrorMessage(), data));
        });
      }
    });
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

  @WorkerThread
  protected void saveLocalSource(@NonNull RequestType item) {}

  protected void onFetchFailed(){}

  public LiveData<Resource<ResultType>> asLiveData() {
    return result;
  }
}
