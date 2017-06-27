package com.example.vn008xw.carbeat.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by vn008xw on 6/25/17.
 */

public class AutoClearedValue<T> {
  private T value;
  public AutoClearedValue(Fragment fragment, T value) {
    FragmentManager fragmentManager = fragment.getFragmentManager();
    fragmentManager.registerFragmentLifecycleCallbacks(
            new FragmentManager.FragmentLifecycleCallbacks() {
              @Override
              public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                AutoClearedValue.this.value = null;
                fragmentManager.unregisterFragmentLifecycleCallbacks(this);
              }
            },false);
    this.value = value;
  }

  public T get() {
    return value;
  }
}
