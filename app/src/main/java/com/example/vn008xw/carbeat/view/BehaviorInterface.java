package com.example.vn008xw.carbeat.view;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vn008xw on 7/23/17.
 */

public interface BehaviorInterface {

  void onTouchEvent(@NonNull final View view, @NonNull MotionEvent motionEvent);
}
