package com.example.vn008xw.carbeat.view;

import android.support.annotation.MenuRes;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by vn008xw on 7/5/17.
 */

public interface ExpandableFabContract {

  void setMenu(@MenuRes int resId);
  void toggleExpanded();
  void setMenuExpanded(boolean expanded);
  FloatingActionButton getFabButton();
}
