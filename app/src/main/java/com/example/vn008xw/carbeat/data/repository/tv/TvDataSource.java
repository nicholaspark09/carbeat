package com.example.vn008xw.carbeat.data.repository.tv;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.Show;
import com.example.vn008xw.carbeat.data.vo.ShowResult;


public interface TvDataSource {
  LiveData<Resource<ShowResult>> getPopularShows(int page);

  LiveData<Resource<Show>> getShow(int showId);

  void refresh();
}
