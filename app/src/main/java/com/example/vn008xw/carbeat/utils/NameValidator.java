package com.example.vn008xw.carbeat.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.EditText;

/**
 * Created by vn008xw on 7/28/17.
 */

public class NameValidator extends Validator<EditText> {

  public NameValidator(@StringRes int errorMessage) {
    super(errorMessage);
  }

  @Override
  public boolean isValid(@NonNull EditText value) {
    return !value.getText().toString().isEmpty();
  }
}
