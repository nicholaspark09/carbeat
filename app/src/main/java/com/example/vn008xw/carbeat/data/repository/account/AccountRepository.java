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
  final MutableLiveData<Long> loggedId = new MutableLiveData<>();

  @Inject
  public AccountRepository(@NonNull AppExecutors appExecutors,
                           @NonNull AccountDao accountDao,
                           @NonNull SharedPreferences sharedPreferences) {
    this.appExecutors = appExecutors;
    this.accountDao = accountDao;
    this.sharedPreferences = sharedPreferences;
  }

  @Override
  public LiveData<Resource<Long>> insertAccount(@NonNull String firstName,
                                                @NonNull String lastName,
                                                @NonNull String email,
                                                @NonNull String pin) {
    final MediatorLiveData<Resource<Long>> result = new MediatorLiveData<>();
    final Account account =
            new Account(firstName, lastName, email, pin);
    result.setValue(Resource.loading(null));
    appExecutors.diskIO().execute(() -> {
      long inserted = accountDao.insertAccount(account);
      Log.d(TAG, "The id was: " + inserted);
      sharedPreferences.edit().putLong(KEY_ACCOUNT_ID, inserted).commit();
      appExecutors.mainThread().execute(() -> result.setValue(Resource.success(inserted)));
    });
    return result;
  }

  @Override
  public LiveData<Resource<Long>> loggedInAccountId() {
    final MediatorLiveData<Resource<Long>> result = new MediatorLiveData<>();
    appExecutors.diskIO().execute(() -> {
      long id = sharedPreferences.getLong(KEY_ACCOUNT_ID, -1);
      Log.d(TAG, "The id was: " + id);
      appExecutors.mainThread().execute(() -> {
        loggedId.setValue(id);
        if (id != -1) {
          result.setValue(Resource.success(id));
        }
        else
          result.setValue(Resource.error("Didn't have a logged in user", null));
      });
    });
    return result;
  }

  @Override
  public LiveData<Resource<Account>> findAccountById(@NonNull Long id) {
    final MutableLiveData<Resource<Account>> result = new MutableLiveData<>();
    result.setValue(Resource.loading(null));
    appExecutors.diskIO().execute(() -> {
      Log.d(TAG, "Trying to find an account with id: " + id);
      final LiveData<Account> account = accountDao.findAccountById(id);
      appExecutors.mainThread().execute(() -> {
        if (account.getValue() != null) {
          sharedPreferences.edit().putLong(KEY_ACCOUNT_ID, id);
          result.setValue(Resource.success(account.getValue()));
        } else {
          result.setValue(Resource.error("Couldn't find the account", null));
        }
      });
    });
    return result;
  }

  @Override
  public LiveData<Resource<Account>> signIn(@NonNull String email, @NonNull String pin) {
    return null;
  }

  @Override
  public void logout() {
    Log.d(TAG, "Logging out or trying to...");
    loggedId.setValue(null);
  }
}
