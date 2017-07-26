package com.example.vn008xw.carbeat.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.repository.account.AccountRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Account;
import com.example.vn008xw.carbeat.data.vo.Resource;

import javax.inject.Inject;


public class AccountViewModel extends ViewModel {

  @VisibleForTesting
  final AccountRepository accountRepository;
  @VisibleForTesting
  final LiveData<Resource<Integer>> accountId;
  @VisibleForTesting
  final LiveData<Resource<Account>> account;

  @SuppressWarnings("unchecked")
  @Inject
  public AccountViewModel(@NonNull AccountRepository accountRepository) {
    this.accountRepository = accountRepository;

    accountId = accountRepository.loggedInAccountId();

    account = Transformations.switchMap(accountId, id -> {
      if (id == null || id.data == -1) return AbsentLiveData.create();
      else return accountRepository.findAccountById(id.data);
    });
  }

  void createAccount(@NonNull String firstName,
                     @NonNull String lastName,
                     @NonNull String email,
                     @NonNull String pin) {
    accountRepository.insertAccount(firstName, lastName,
            email, pin);
  }

  LiveData<Resource<Integer>> getLoggedId() {
    return accountId;
  }

  LiveData<Resource<Account>> findAccountById() {
    return account;
  }

  void logout() {
    accountRepository.logout();
  }
}
