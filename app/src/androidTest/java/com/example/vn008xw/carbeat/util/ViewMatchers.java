package com.example.vn008xw.carbeat.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by vn008xw on 7/12/17.
 */

public class ViewMatchers {

  public static class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;

    public DrawableMatcher(int resourceId) {
      super(View.class);
      expectedId = resourceId;
    }

    @Override
    protected boolean matchesSafely(View item) {
      if (!(item instanceof ImageView))
        return false;
      final ImageView imageView = (ImageView)item;
      if (expectedId < 0) return imageView.getDrawable() == null;
      Resources resources = item.getContext().getResources();
      Drawable expectedDrawable = resources.getDrawable(expectedId);
      if (expectedDrawable == null) {
        return false;
      }
      Bitmap bitmap = getBitmap (imageView.getDrawable());
      Bitmap otherBitmap = getBitmap(expectedDrawable);
      return bitmap.sameAs(otherBitmap);
    }

    @Override
    public void describeTo(Description description) {
      description.appendText("with drawable from resource id: ");
      description.appendValue(expectedId);
    }

    private Bitmap getBitmap(Drawable drawable){
      Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
              drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);
      return bitmap;
    }
  }

  public static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {

    return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {

      @Override
      public void describeTo(Description description) {
        description.appendText("with toolbar title: ");
        textMatcher.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(Toolbar item) {
        return textMatcher.matches(item.getTitle());
      }
    };
  }

  public static Matcher<View> withDrawable(final int resourceId) {
    return new DrawableMatcher(resourceId);
  }
}
