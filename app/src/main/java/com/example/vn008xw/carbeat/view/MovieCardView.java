package com.example.vn008xw.carbeat.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import com.example.vn008xw.carbeat.R;

/**
 * Created by vn008xw on 7/1/17.
 */

public class MovieCardView extends FrameLayout {

  private boolean flingable;
  private boolean moveable;
  private boolean peekable;
  private String title;
  private String description;

  private Paint textPaint;
  private ValueAnimator valueAnimator;
  private GestureDetector gestureDetector;
  private ObjectAnimator objectAnimator;
  private ElevationBehavior mElevationBehavior;

  public static final int FLING_VELOCITY_DOWNSCALE = 4;
  public static final int AUTOCENTER_ANIM_DURATION = 250;

  public MovieCardView(Context context) {
    super(context);
    init();
  }

  public MovieCardView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray typedArray = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.MovieCardView,
            0,0
    );
    try {
      flingable = typedArray.getBoolean(R.styleable.MovieCardView_flingable, false);
      peekable = typedArray.getBoolean(R.styleable.MovieCardView_peekable, false);
      title = typedArray.getString(R.styleable.MovieCardView_title);
      description = typedArray.getString(R.styleable.MovieCardView_description);
    }finally {
      typedArray.recycle();
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      int width =  w + getPaddingLeft() + getPaddingRight();
      int height = h + getPaddingTop() + getPaddingBottom();

      setOutlineProvider(new ShadowOutline(width, height));
    }
  }

  private void init() {
    final Resources resources = getResources();
    mElevationBehavior = new ElevationBehavior(resources.getDimensionPixelSize(R.dimen.card_default_elevation));

  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    mElevationBehavior.onTouchEvent(this, event);
    return super.onTouchEvent(event);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private class ShadowOutline extends ViewOutlineProvider {
    int width;
    int height;

    ShadowOutline(int width, int height) {
      this.width = width;
      this.height = height;
    }

    @Override
    public void getOutline(View view, Outline outline) {
      outline.setRect(0,0, width, height);
    }
  }
}
