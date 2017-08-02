package com.example.vn008xw.carbeat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;

import com.example.vn008xw.carbeat.R;

/**
 * Created by vn008xw on 7/31/17.
 */

public class PhoneView extends AppCompatEditText {

  private boolean americanNumber = false;

  public PhoneView(Context context) {
    super(context);
  }

  public PhoneView(Context context, AttributeSet attrs) {
    super(context, attrs);

    final TypedArray array = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.PhoneView,
            0, 0
    );

    try {
      americanNumber = array.getBoolean(R.styleable.PhoneView_american, true);
    } finally {
      array.recycle();
    }
    setInputType(InputType.TYPE_CLASS_NUMBER);
  }

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    if (isFocused() && lengthBefore != lengthAfter) {
      Log.d("PhoneView", "The number is " + text);
      if (text.length() == 3) {
        text = String.format("(%s) ", text);
      }
      setText(text);
      setSelection(text.length());
    }
  }
}
