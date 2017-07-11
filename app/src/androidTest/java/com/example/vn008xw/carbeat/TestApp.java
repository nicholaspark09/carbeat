package com.example.vn008xw.carbeat;

import android.app.Application;

/**
 * Used to prevent initializing dependency injection
 */

public class TestApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
  }
}
