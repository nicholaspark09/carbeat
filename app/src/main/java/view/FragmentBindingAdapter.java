package view;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.utils.DrawableUtilCompat;

import javax.inject.Inject;

/**
 * Created by vn008xw on 7/11/17.
 */

public class FragmentBindingAdapter {

  final Fragment fragment;

  @Inject
  public FragmentBindingAdapter(Fragment fragment) {
    this.fragment = fragment;
  }

  @BindingAdapter("imageResource")
  public void setImageResource(@NonNull ImageView imageView, @DrawableRes int resource) {
    imageView.setImageResource(resource);
  }

  @BindingAdapter({"imageUrl"})
  public void loadImage(@NonNull ImageView imageView, @Nullable String url) {
    Glide.with(fragment)
            .load(url)
            .placeholder(R.drawable.ic_no_image)
            .error(DrawableUtilCompat.getDrawable(imageView.getContext(), R.drawable.ic_no_image))
            .into(imageView);
  }

  @BindingAdapter({"hidden"})
  public void bindVisibility(View view, boolean hidden) {
    view.setVisibility(hidden ? View.GONE : View.VISIBLE);
  }
}
