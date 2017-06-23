package com.example.vn008xw.carbeat.data.vo;

import android.arch.lifecycle.LiveData;

/**
 * Created by vn008xw on 6/22/17.
 */

public class AbsentLiveData extends LiveData {

  private AbsentLiveData() {
    postValue(null);
  }

  public static <T> LiveData<T> create() {
    return new AbsentLiveData();
  }
}
