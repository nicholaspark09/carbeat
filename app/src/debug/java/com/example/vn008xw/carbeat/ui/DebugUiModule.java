package com.example.vn008xw.carbeat.ui;

import com.example.vn008xw.carbeat.ViewContainer;
import com.example.vn008xw.carbeat.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugUiModule {

  @Provides
  @ApplicationScope
  ViewContainer provideViewContainer() {
    return new DebugViewContainer();
  }
}
