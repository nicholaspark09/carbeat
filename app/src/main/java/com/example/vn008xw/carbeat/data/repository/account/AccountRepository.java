package com.example.vn008xw.carbeat.data.repository.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.data.db.AccountDao;
import com.example.vn008xw.carbeat.data.vo.Account;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import javax.inject.Inject;

/**
 *  Loads data from local db
 *  There is no need to cache the data as it's coming directly from the db
 *    THIS IS NOT CONNECTED TO A BACKEND SERVICE
 */
@ApplicationScope
public class AccountRepository implements AccountDataSource {

  @NonNull
  private static final String TAG = AccountRepository.class.getSimpleName();
  @NonNull private static final String KEY_ACCOUNT_ID = "key:account_id";

  @VisibleForTesting
  final AppExecutors appExecutors;
  @VisibleForTesting
  final AccountDao accountDao;
  @VisibleForTesting
  final SharedPreferences sharedPreferences;
  @VisibleForTesting
  final MutableLiveData<Integer> loggedId = new MutableLiveData<>();

  @Inject
  public AccountRepository(@NonNull AppExecutors appExecutors,
                           @NonNull AccountDao accountDao,
                           @NonNull SharedPreferences sharedPreferences) {
    this.appExecutors = appExecutors;
    this.accountDao = accountDao;
    this.sharedPreferences = sharedPreferences;
  }

  @Override
  public void insertAccount(@NonNull String firstName, @NonNull String lastName,
                            @NonNull String email, @NonNull String pin) {

    appExecutors.diskIO().execute(() -> {
      final Account account =
              new Account(firstName, lastName, email, pin);
      Long id = accountDao.insertAccount(account);
      appExecutors.mainThread().execute(() -> loggedId.setValue(id.intValue()));
    });
  }

  @Override
  public LiveData<Resource<Integer>> loggedInAccountId() {
    final MediatorLiveData<Resource<Integer>> result = new MediatorLiveData<>();
    appExecutors.diskIO().execute(() -> {
      appExecutors.mainThread().execute(() -> {
        result.addSource(loggedId, observer -> {
          if (observer == -1) {
            Log.d(TAG, "The user id was set to -1");
            result.setValue(Resource.error("Logged out", null));
          }
          else
            result.setValue(Resource.success(observer));
        });
      });
      loggedId.setValue(sharedPreferences.getInt(KEY_ACCOUNT_ID, -1));
    });
    return result;
  }

  @Override
  public LiveData<Resource<Account>> findAccountById(@NonNull Integer id) {
    return null;
  }

  @Override
  public LiveData<Resource<Account>> signIn(@NonNull String email, @NonNull String pin) {
    return null;
  }

  @Override
  public void logout() {
    Log.d(TAG, "Logging out or trying to...");
    loggedId.setValue(-1);
  }
}
