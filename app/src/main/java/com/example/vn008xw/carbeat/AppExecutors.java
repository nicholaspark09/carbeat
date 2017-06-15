package com.example.vn008xw.carbeat;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.DaggerUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * Created by vn008xw on 6/11/17.
 */
@ApplicationScope
public class AppExecutors {

  @NonNull private final static int NETWORK_THREAD_POOL = 3;

  private final Executor diskIO;

  private final Executor networkIO;

  private final Executor mainThread;

  public AppExecutors(Executor diskIO,
                      Executor networkIO,
                      Executor mainThread) {
    this.diskIO = diskIO;
    this.networkIO = networkIO;
    this.mainThread = mainThread;
  }

  @Inject
  public AppExecutors() {
    this(Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(NETWORK_THREAD_POOL),
            new MainThreadExecutor());
    DaggerUtils.track(this);
  }

  public Executor diskIO() {
    return diskIO;
  }

  public Executor networkIO() {
    return networkIO;
  }

  public Executor mainThread() {
    return mainThread;
  }

  private static class MainThreadExecutor implements Executor {

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
      mainThreadHandler.post(command);
    }
  }
}