package com.example.vn008xw.carbeat.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.MotionEvent;
import android.view.View;


public class ElevationBehavior implements BehaviorInterface {

  private final float mPressed;
  private final float mNormal;

  public ElevationBehavior(float pressedHeight) {
    mNormal = 0;
    mPressed = pressedHeight;
  }

  @Override
  public void onTouchEvent(@NonNull View view, @NonNull MotionEvent motionEvent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      if (view.isClickable() && view.isEnabled()) {
        final int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
          view.animate().setInterpolator(new FastOutSlowInInterpolator()).translationZ(mPressed);
        } else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_OUTSIDE ||
                action == MotionEvent.ACTION_CANCEL) {
          view.animate().setInterpolator(new FastOutSlowInInterpolator()).translationZ(mNormal);
        }
      }
    }
  }
}
