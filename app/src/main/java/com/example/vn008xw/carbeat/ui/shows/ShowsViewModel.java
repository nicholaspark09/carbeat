package com.example.vn008xw.carbeat.ui.shows;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.vn008xw.carbeat.data.repository.tv.TvRepository;
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.ShowResult;

import javax.inject.Inject;

public class ShowsViewModel extends ViewModel {

  @VisibleForTesting
  final MutableLiveData<Integer> page = new MutableLiveData<>();
  @VisibleForTesting
  final LiveData<Resource<ShowResult>> showResults;
  @VisibleForTesting
  final TvRepository repository;

  @SuppressWarnings("unchecked")
  @Inject
  public ShowsViewModel(@NonNull TvRepository tvRepository) {
    repository = tvRepository;
    page.setValue(1);

    showResults = Transformations.switchMap(page, integer -> {
      if (integer == null) return AbsentLiveData.create();
      return repository.getPopularShows(page.getValue());
    });
  }

  public LiveData<Resource<ShowResult>> showStream() {
    return showResults;
  }

  public void loadMore() {
    page.postValue(page.getValue() + 1);
  }

  public void refresh() {
    repository.refresh();
    page.postValue(1);
  }
}
