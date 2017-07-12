package com.example.vn008xw.carbeat.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by vn008xw on 7/11/17.
 */

public class ViewModelUtil {

  private ViewModelUtil() {
    throw new AssertionError("private constructor");
  }

  public static <T extends ViewModel> ViewModelProvider.Factory createFor(T model) {
    return new ViewModelProvider.Factory() {
      @Override
      public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(model.getClass()))
          return (T) model;
        throw new IllegalArgumentException("unexepcted model class " + modelClass);
      }
    };
  }
}
