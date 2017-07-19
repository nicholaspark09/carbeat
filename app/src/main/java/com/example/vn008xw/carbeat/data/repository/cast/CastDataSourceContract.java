package com.example.vn008xw.carbeat.data.repository.cast;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Cast;
import com.example.vn008xw.carbeat.data.vo.Resource;

import java.util.List;

/**
 * Created by vn008xw on 7/18/17.
 */

interface CastDataSourceContract {

  LiveData<Resource<Cast>> getCast(@NonNull int castId);
  LiveData<Resource<List<Cast>>> getMovieCast(@NonNull int movieId);
  void saveCast(@NonNull Cast cast);
  void deleteCast(@NonNull Cast cast);
  void deleteAll();
  void refresh();
}
