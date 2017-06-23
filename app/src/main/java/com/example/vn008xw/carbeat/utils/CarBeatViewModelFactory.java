package com.example.vn008xw.carbeat.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.vn008xw.carbeat.di.ApplicationScope;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by vn008xw on 6/22/17.
 */
@ApplicationScope
public class CarBeatViewModelFactory implements ViewModelProvider.Factory {

  private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

  @Inject
  public CarBeatViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
    this.creators = creators;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    Provider<? extends ViewModel> creator = creators.get(modelClass);
    if (creator == null) {
      for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
        if (modelClass.isAssignableFrom(entry.getKey())) {
          creator = entry.getValue();
          break;
        }
      }
    }
    if (creator == null) {
      throw new IllegalArgumentException("unknown model class " + modelClass);
    }
    try {
      return (T) creator.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
