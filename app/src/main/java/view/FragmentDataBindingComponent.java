package view;


import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * Created by vn008xw on 7/11/17.
 */

public class FragmentDataBindingComponent implements DataBindingComponent {

  private final FragmentBindingAdapter adapter;

  public FragmentDataBindingComponent(Fragment fragment) {
    this.adapter = new FragmentBindingAdapter(fragment);
  }

  @Override
  public FragmentBindingAdapter getFragmentBindingAdapter() {
    return adapter;
  }
}
