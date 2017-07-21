package com.example.vn008xw.carbeat;

import android.app.Application;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.example.vn008xw.carbeat.di.DaggerService;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import static android.os.StrictMode.setThreadPolicy;
import static android.os.StrictMode.setVmPolicy;

/**
 * Created by vn008xw on 6/6/17.
 */

public class CarBeatApp extends MultiDexApplication {

  @Inject
  Application app;
  @Inject
  AppExecutors appExecutors;

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
      setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    buildComponentAndInject();
  }

  @Override
  public Object getSystemService(String name) {
    if (name.equalsIgnoreCase(DaggerService.SERVICE_NAME) && appComponent != null) {
      return appComponent;
    }
    return super.getSystemService(name);
  }

  private void buildComponentAndInject() {
    appComponent = CarBeatComponent.Initializer.init(this);
    appComponent.inject(this);
  }

  public AppComponent component() {
    return appComponent;
  }
}
