package com.example.vn008xw.carbeat;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.vn008xw.carbeat.utils.ViewUtil;

/**
 * Created by vn008xw on 7/31/17.
 */

public interface ViewContainer {

  ViewContainer DEFAULT = activity -> ViewUtil.findViewById(activity, android.R.id.content);

  ViewGroup forActivity(Activity activity);
}
