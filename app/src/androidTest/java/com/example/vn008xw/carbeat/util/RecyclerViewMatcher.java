package com.example.vn008xw.carbeat.util;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by vn008xw on 7/23/17.
 */

public class RecyclerViewMatcher {

  private final int recyclerViewId;

  public RecyclerViewMatcher(int recyclerViewId) {
    this.recyclerViewId = recyclerViewId;
  }

  public Matcher<View> atPosition(final int position) {
    return atPositionOnView(position, -1);
  }

  public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

    return new TypeSafeMatcher<View>() {

      Resources resources = null;
      View childView;

      @Override
      protected boolean matchesSafely(View item) {

        this.resources = item.getResources();

        if (childView == null) {
          RecyclerView recyclerView =
                  (RecyclerView) item.getRootView().findViewById(recyclerViewId);
          if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
              childView = viewHolder.itemView;
            }
          } else {
            return false;
          }
        }
        if (targetViewId == -1) {
          return item == childView;
        } else {
          View targetView = childView.findViewById(targetViewId);
          return item == targetView;
        }
      }

      @Override
      public void describeTo(Description description) {
        String id = Integer.toString(recyclerViewId);
        if (this.resources != null) {
          try {

          }catch (Resources.NotFoundException e) {
            id = String.format("%s (resource name not found", recyclerViewId);
          }
        }
        description.appendText("RecyclerView with id: " + id + " at position: " + position);
      }
    };
  }
}
