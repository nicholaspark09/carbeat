package com.example.vn008xw.carbeat.ui.cast;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.base.BaseActivity;
import com.example.vn008xw.carbeat.utils.ActivityUtil;

import org.jetbrains.annotations.NotNull;

public class CastActivity extends BaseActivity {

  @NonNull private static final String MOVIE__ID_KEY = "cast:movie_id";
  @NonNull private int movieId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cast);

    final CastFragment fragment = findOrCreate();
    ActivityUtil.replaceFragmentInActivity(getSupportFragmentManager(), fragment, R.id.container);
  }

  @NonNull
  private CastFragment findOrCreate() {
    final CastFragment fragment = (CastFragment) getSupportFragmentManager().findFragmentById(R.id.container);
    if (fragment != null) return fragment;
    else return CastFragment.Companion.newInstance();
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
