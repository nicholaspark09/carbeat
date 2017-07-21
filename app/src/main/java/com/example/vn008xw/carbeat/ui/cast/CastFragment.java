package com.example.vn008xw.carbeat.ui.cast;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentCastBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import static com.example.vn008xw.carbeat.data.vo.Status.LOADING;
import static com.example.vn008xw.carbeat.data.vo.Status.SUCCESS;

/**
 * Created by vn008xw on 7/20/17.
 */

public class CastFragment extends BaseView {

  @Inject
  @VisibleForTesting
  public ViewModelProvider.Factory viewModelFactory;

  @VisibleForTesting
  AutoClearedValue<FragmentCastBinding> binding;
  @VisibleForTesting
  AutoClearedValue<CastListAdapter> castAdapter;
  @VisibleForTesting
  CastViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final FragmentCastBinding castBinding = FragmentCastBinding.inflate(inflater, container, false);
    setHasOptionsMenu(true);
    binding = new AutoClearedValue(this, castBinding);
    return castBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CastViewModel.class);

    final CastListAdapter adapter = new CastListAdapter();
    castAdapter = new AutoClearedValue<>(this, adapter);


    viewModel.getCast().observe(this, observer -> {
      if (observer.status == SUCCESS) {
        castAdapter.get().replace(observer.data);
      }
      setLoading(observer.status);
    });

    // This initiates the cast call as you first retrieve the movie from the movierepo
    viewModel.findMovie();
  }

  private void setLoading(@NonNull Status status) {
    if (status == LOADING) {
      binding.get().setLoading(true);
      return;
    }
    binding.get().setLoading(false);
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }

  public static CastFragment newInstance() {
    return new CastFragment();
  }
}
