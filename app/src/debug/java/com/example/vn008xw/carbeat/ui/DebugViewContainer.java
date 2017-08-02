package com.example.vn008xw.carbeat.ui;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.ViewContainer;

public final class DebugViewContainer implements ViewContainer {

  @Override
  public ViewGroup forActivity(Activity activity) {

    activity.setContentView(R.layout.debug_activity_frame);


    return null;
  }
}
