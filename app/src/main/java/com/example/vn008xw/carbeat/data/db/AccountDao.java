package com.example.vn008xw.carbeat.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Account;

@Dao
public abstract class AccountDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract Long insertAccount(@NonNull Account account);

  @Query("SELECT * FROM account WHERE id = :id LIMIT 1")
  public abstract LiveData<Account> findAccountById(@NonNull int id);

  @Query("SELECT * FROM account WHERE email = :email LIMIT 1")
  public abstract LiveData<Account> loadAccount(@NonNull String email,
                                                @NonNull String pin);

  @Delete
  public abstract void deleteAccount(@NonNull Account account);
}
