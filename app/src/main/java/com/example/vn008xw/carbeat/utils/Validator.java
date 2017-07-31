package com.example.vn008xw.carbeat.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;


public abstract class Validator<T> {

  private final int mErrorMessage;

  public Validator(@StringRes int errorMessage) {
    mErrorMessage = errorMessage;
  }

  public abstract boolean isValid(@NonNull T value);

  @NonNull
  public String getErrorMessage(@NonNull Context context) {
    return context.getString(mErrorMessage);
  }
}
