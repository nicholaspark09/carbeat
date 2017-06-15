package com.example.vn008xw.carbeat.utils;

import android.util.Log;

import com.example.vn008xw.carbeat.BuildConfig;

/**
 * Created by vn008xw on 6/6/17.
 */

public final class DaggerUtils {

    private DaggerUtils(){ throw new AssertionError("No instances"); }

    public static <T> T track(T object) {
        if (BuildConfig.DEBUG) {
            Log.d("Dagger Created: ", object.getClass().getSimpleName());
        }
        return object;
    }
}
