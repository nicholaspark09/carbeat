package com.example.vn008xw.carbeat.ui.cast;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

public class CastActivity extends BaseActivity {

  @NonNull private static final String MOVIE__ID_KEY = "cast:movie_id";
  @NonNull private int movieId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cast);

  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
