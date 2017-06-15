package com.example.vn008xw.carbeat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class ViewUtil {

    @UiThread
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    @UiThread
    @NonNull
    @SuppressWarnings("unchecked")
    public static <T extends View> T inflate(@LayoutRes int resId, @NonNull ViewGroup parent) {
        return (T) LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    @UiThread
    public static void setText(@NonNull View parent, @IdRes int viewId, @StringRes int textResId) {
        ViewUtil.<TextView>findViewById(parent, viewId).setText(textResId);
    }

    @UiThread
    public static void setText(@NonNull View parent, @IdRes int viewId, @NonNull String text) {
        ViewUtil.<TextView>findViewById(parent, viewId).setText(text);
    }

    @UiThread
    public static void setText(@NonNull View parent, @IdRes int viewId, @StringRes int textResId, Object... args) {
        setText(parent, viewId, parent.getResources().getString(textResId, args));
    }

    /**
     * Get the text for a view.
     *
     * @param parent The parent of the view
     * @param viewId The view ID of the view to get the text from
     * @return The text of the view or {@code null} if not a valid text view.
     * @see #getText(View)
     */
    @UiThread
    public static CharSequence getText(@NonNull View parent, @IdRes int viewId) {
        return getText(parent.findViewById(viewId));
    }

    /**
     * Get the text for a view. The View must be either a descendant of {@link TextView} or
     * {@link TextInputLayout}.
     *
     * @param view The view to get the text from
     * @return The text of the view or {@code null} if not a valid text view.
     * @see #getText(View, int)
     */
    @UiThread
    @NonNull
    public static CharSequence getText(@NonNull View view) {
        CharSequence text = null;
        if (view instanceof TextView) {
            text = ((TextView) view).getText();
        } else if (view instanceof TextInputLayout) {
            //noinspection ConstantConditions
            text = ((TextInputLayout) view).getEditText().getText();
        }

        return text != null ? text : "";
    }

    @UiThread
    @NonNull
    public static String getTextAsString(@NonNull View view) {
        return getText(view).toString();
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @UiThread
    public static void hideKeyboard(@NonNull View view) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @UiThread
    public static void showKeyboard(@NonNull View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static int getStringVisibility(String string) {
        return getStringVisibility(new String[]{string});
    }

    public static int getStringVisibility(String[] strings) {
        for (String str : strings) {
            if (!TextUtils.isEmpty(str)) {
                return View.VISIBLE;
            }
        }

        return View.GONE;
    }

    @UiThread
    public static void setText(@NonNull TextInputLayout textInputLayout, @Nullable CharSequence text) {
        final EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            textInputLayout.getEditText().setText(text);
        }
    }

    @UiThread
    public static void hideKeyboard(@Nullable Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            hideKeyboard(activity.getCurrentFocus());
        }
    }

    @UiThread
    public static int getStatusBarHeight(@NonNull Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        } else {
            // TODO: 10/20/16 do we want to fallback?

        }
        return result;
    }
}
