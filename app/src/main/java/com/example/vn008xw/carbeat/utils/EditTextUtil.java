package com.example.vn008xw.carbeat.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;

/**
 * Created by vn008xw on 7/28/17.
 */

public final class EditTextUtil {

  private EditTextUtil() {
    throw new AssertionError("No instances");
  }

  public static boolean showErrorInParent(@NonNull View view, @NonNull String error) {
    if (view.getParent() instanceof TextInputLayout) {
      ((TextInputLayout) view.getParent()).setError(error);
      return true;
    }
    return false;
  }

  public static void clearErrorInParent(@NonNull View view) {
    if (view.getParent() instanceof TextInputLayout) {
      ((TextInputLayout) view.getParent()).setError("");
    }
  }
}
