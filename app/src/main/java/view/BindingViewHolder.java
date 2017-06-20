package view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

  private final T mViewDataBinding;

  public BindingViewHolder(View itemView) {
    super(itemView);
    mViewDataBinding = DataBindingUtil.bind(itemView);
  }

  public BindingViewHolder(T binding) {
    super(binding.getRoot());
    mViewDataBinding = binding;
  }

  public T getViewDataBinding() {
    return mViewDataBinding;
  }
}
