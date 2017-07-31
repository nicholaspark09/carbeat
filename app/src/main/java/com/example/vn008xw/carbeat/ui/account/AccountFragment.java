package com.example.vn008xw.carbeat.ui.account;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.base.BaseView;
import com.example.vn008xw.carbeat.data.vo.Status;
import com.example.vn008xw.carbeat.databinding.FragmentAccountBinding;
import com.example.vn008xw.carbeat.utils.AutoClearedValue;
import com.example.vn008xw.carbeat.utils.EditTextUtil;
import com.example.vn008xw.carbeat.utils.Validator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.example.vn008xw.carbeat.data.vo.Status.LOADING;
import static com.example.vn008xw.carbeat.data.vo.Status.SUCCESS;

public class AccountFragment extends BaseView {

  @NonNull
  private static final String TAG = AccountFragment.class.getSimpleName();

  @VisibleForTesting
  AutoClearedValue<FragmentAccountBinding> mBinding;
  @VisibleForTesting
  AccountViewModel mViewModel;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @VisibleForTesting
  final HashMap<Validator, Object> mValidators = new HashMap<>();

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final FragmentAccountBinding binding = FragmentAccountBinding.inflate(inflater, container, false);
    binding.setLoading(true);
    setHasOptionsMenu(true);
    mBinding = new AutoClearedValue<>(this, binding);
    return binding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AccountViewModel.class);

    mViewModel.getLoggedId().observe(this, observer -> {
      mBinding.get().setLoading(false);
      if (observer.status == SUCCESS && observer.data != null)
        mViewModel.setAccountId(observer.data);
      updateLoading(observer.status);
    });

    mViewModel.getAccount().observe(this, observer -> {
      if (observer.status == SUCCESS) {
        Log.d(TAG, "You have an account: " + observer.data);
        mBinding.get().setAccount(observer.data);
      }else {
        Log.d(TAG, "You don't have an account, you have an error: " + observer.message);
      }
    });
    setupValidators();
    setButtonListener();
  }

  private void updateLoading(@NonNull Status status) {
    mBinding.get().setLoading(status == LOADING);
  }

  private void setButtonListener() {
    mBinding.get().saveButton.setOnClickListener((v) -> {
      v.setEnabled(false);

      boolean passedValidators = true;
      for (Map.Entry<Validator, Object> entry : mValidators.entrySet()) {
        final Validator validator = entry.getKey();
        if (!validator.isValid(entry.getValue())) {
          v.setEnabled(true);
          boolean errorShown = false;
          if (entry.getValue() instanceof View) {
            errorShown = EditTextUtil.showErrorInParent((View) entry.getValue(), validator.getErrorMessage(getContext()));
          }
          if (!errorShown)
            Toast.makeText(getContext(), validator.getErrorMessage(getContext()), Toast.LENGTH_SHORT).show();
          passedValidators = false;
        } else {
          EditTextUtil.clearErrorInParent((View) entry.getValue());
        }
      }

      if (passedValidators) {
        mViewModel.createAccount(
                mBinding.get().firstName.getText().toString(),
                mBinding.get().lastName.getText().toString(),
                mBinding.get().email.getText().toString(),
                "0909"
        ).observe(this, observer->{
          if (observer != null) {

          }
        });
      }
    });
  }

  private void setupValidators() {
    mValidators.put(new Validator(R.string.error_first_name) {
      @Override
      public boolean isValid(@NonNull Object value) {
        return !mBinding.get().firstName.getText().toString().isEmpty();
      }
    }, mBinding.get().firstName);

    mValidators.put(new Validator(R.string.error_last_name) {
      @Override
      public boolean isValid(@NonNull Object value) {
        return !mBinding.get().lastName.getText().toString().isEmpty();
      }
    }, mBinding.get().lastName);

    mValidators.put(new Validator(R.string.error_email) {
      @Override
      public boolean isValid(@NonNull Object value) {
        return !mBinding.get().email.getText().toString().isEmpty();
      }
    }, mBinding.get().email);
  }

  @Override
  public void inject(@NotNull AppComponent appComponent) {
    appComponent.inject(this);
  }

  public static AccountFragment newInstance() {
    return new AccountFragment();
  }
}
