package com.example.vn008xw.carbeat.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatDrawableManager

/**
 * Created by vn008xw on 6/11/17.
 */
class DrawableUtil {

    companion object {
        @JvmStatic
        fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable {
            return AppCompatDrawableManager.get().getDrawable(context, resId)
        }
    }
}