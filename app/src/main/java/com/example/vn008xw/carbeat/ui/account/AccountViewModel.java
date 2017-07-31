package com.example.vn008xw.carbeat.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
  final MutableLiveData<Long> accountId;
  @VisibleForTesting
  final LiveData<Resource<Account>> account;

  @SuppressWarnings("unchecked")
  @Inject
  public AccountViewModel(@NonNull AccountRepository accountRepository) {
    this.accountRepository = accountRepository;

    accountId = new MutableLiveData<>();

    account = Transformations.switchMap(accountId, id -> {
      if (id == null) return AbsentLiveData.create();
      else return accountRepository.findAccountById(id);
    });
  }

  LiveData<Resource<Long>> createAccount(@NonNull String firstName,
                     @NonNull String lastName,
                     @NonNull String email,
                     @NonNull String pin) {
    return accountRepository.insertAccount(firstName, lastName,
            email, pin);
  }

  LiveData<Resource<Long>> getLoggedId() {
    return accountRepository.loggedInAccountId();
  }

  LiveData<Resource<Account>> getAccount() {
    return account;
  }

  void setAccountId(Long id) {
    accountId.setValue(id);
  }

  void logout() {
    accountRepository.logout();
  }
}
