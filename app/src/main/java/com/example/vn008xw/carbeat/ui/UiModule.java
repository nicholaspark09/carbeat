package com.example.vn008xw.carbeat.ui;

import com.example.vn008xw.carbeat.ViewContainer;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vn008xw on 6/6/17.
 */
@Module
public final class UiModule {

  @Provides
  @ApplicationScope
  ViewContainer provideViewContainer() {
    return ViewContainer.DEFAULT;
  }
}
