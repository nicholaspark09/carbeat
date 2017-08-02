package com.example.vn008xw.carbeat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.vn008xw.carbeat.R;

/**
 * Created by vn008xw on 7/31/17.
 */

public class ModalView extends LinearLayout {

  private boolean mAnimate = false;

  public ModalView(Context context) {
    super(context);
  }

  public ModalView(Context context, AttributeSet attrs) {
    super(context, attrs);

    final TypedArray array = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.ModalView,
            0, 0
    );

    try {
      mAnimate = array.getBoolean(R.styleable.ModalView_animate, false);
    } finally {
      array.recycle();
    }

    setOrientation(LinearLayout.VERTICAL);
  }

  @Override
  protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
    if (visibility == View.VISIBLE) {

    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      int width = w + getPaddingLeft() + getPaddingRight();
      int height = h + getPaddingTop() + getPaddingBottom();

      setOutlineProvider(new ShadowOutline(width, height));
    }
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
      outline.setRect(0, 0, width, height);
    }
  }
}
