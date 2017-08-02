package com.example.vn008xw.carbeat.ui.shows;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentShowsBinding;
import com.example.vn008xw.carbeat.ui.NavigationController;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class ShowsFragment extends BaseView {

  @NonNull private static final String TAG = ShowsFragment.class.getSimpleName();
  public static ShowsFragment newInstance = new ShowsFragment();

  @VisibleForTesting
  AutoClearedValue<FragmentShowsBinding> mBinding;
  @VisibleForTesting
  ShowsViewModel mViewModel;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  NavigationController mNavigationController;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    FragmentShowsBinding binding = FragmentShowsBinding.inflate(inflater, container, false);
    mBinding = new AutoClearedValue<>(this, binding);
    return binding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ShowsViewModel.class);

    mViewModel.showStream().observe(this, results -> {
      if (results != null) {
        if (results.status == Status.SUCCESS) {

        } else if (results.status == Status.ERROR) {
          Toast.makeText(getContext(), results.message, Toast.LENGTH_SHORT).show();
        }
      }
      updateLoading(results.status);
    });
  }

  private void updateLoading(@NonNull Status status) {
    mBinding.get().setLoading(status == Status.LOADING);
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
