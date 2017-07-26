package com.example.vn008xw.carbeat.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.repository.account.AccountRepository;
import com.example.vn008xw.carbeat.data.vo.Resource;

import javax.inject.Inject;


public class AccountViewModel extends ViewModel {

  @VisibleForTesting
  final AccountRepository accountRepository;
  @VisibleForTesting
  final LiveData<Resource<Integer>> accountId;

  @SuppressWarnings("unchecked")
  @Inject
  public AccountViewModel(@NonNull AccountRepository accountRepository) {
    this.accountRepository = accountRepository;

    accountId = accountRepository.loggedInAccountId();
  }

  LiveData<Resource<Integer>> getLoggedId() {
    return accountId;
  }

  void logout() {
    accountRepository.logout();
  }
}
