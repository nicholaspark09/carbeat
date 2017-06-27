package com.example.vn008xw.carbeat.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by vn008xw on 6/24/17.
 */

public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

  public final T binding;

  DataBoundViewHolder(T binding) {
    super(binding.getRoot());
    this.binding = binding;
  }
}
