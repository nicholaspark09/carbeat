package com.example.vn008xw.carbeat.view;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * Created by vn008xw on 7/5/17.
 */

public class ExpandableFabView extends LinearLayout {

  // Directions
  private FloatingActionButton mFabButton;

  public ExpandableFabView(Context context) {
    super(context);
  }

  public static class ExpansionAnimator {
    private final Handler mHandler = new Handler();
  }

  enum DIRECTION {
    UP,
    RIGHT,
    DOWN,
    LEFT,
    CIRCULAR
  }
}
