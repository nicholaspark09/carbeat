package com.example.vn008xw.carbeat.data.repository.account;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Account;
import com.example.vn008xw.carbeat.data.vo.Resource;

/**
 * Created by vn008xw on 7/25/17.
 */

public interface AccountDataSource {

  @NonNull
  LiveData<Resource<Long>> insertAccount(@NonNull String firstName,
                                         @NonNull String lastName,
                                         @NonNull String email,
                                         @NonNull String pin);
  @NonNull
  LiveData<Resource<Integer>> loggedInAccountId();

  @NonNull
  LiveData<Resource<Account>> findAccountById(@NonNull Integer id);

  @NonNull
  LiveData<Resource<Account>> signIn(@NonNull String email,
                                     @NonNull String pin);

  void logout();
}
