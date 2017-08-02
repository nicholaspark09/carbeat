package com.example.vn008xw.carbeat.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.vn008xw.carbeat.di.DaggerService;

/**
 * Created by vn008xw on 7/31/17.
 */

public class DebugView extends FrameLayout {


  public DebugView(@NonNull Context context) {
    super(context);
  }

  public DebugView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    DaggerService.getAppComponent(context).inject(this);

  }
}
