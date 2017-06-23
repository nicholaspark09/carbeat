package com.example.vn008xw.carbeat.utils;

/**
 * Created by vn008xw on 6/23/17.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;

public class DrawableUtilCompat {

  public static Drawable tintDrawable(@NonNull Context context, @DrawableRes int drawableResId, @ColorRes int tintResId) {
    Drawable drawable = getDrawable(context, drawableResId);
    drawable = DrawableCompat.wrap(DrawableCompat.unwrap(drawable).mutate());
    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
    DrawableCompat.setTint(drawable, context.getResources().getColor(tintResId));
    return drawable;
  }

  public static Drawable tintDrawable(@NonNull Drawable drawable, @NonNull ColorStateList colorStateList) {
    drawable = DrawableCompat.wrap(DrawableCompat.unwrap(drawable).mutate());
    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
    DrawableCompat.setTintList(drawable, colorStateList);
    return drawable;
  }

  public static Drawable tintDrawable(@NonNull Drawable drawable, @ColorInt int tintInt) {
    drawable = DrawableCompat.wrap(DrawableCompat.unwrap(drawable).mutate());
    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
    DrawableCompat.setTint(drawable, tintInt);
    return drawable;
  }

  public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
    // FIXME: 1/27/17 Solve without hidden API
    //noinspection RestrictedApi
    return AppCompatDrawableManager.get().getDrawable(context, resId);
  }
}
