package com.example.vn008xw.carbeat.data;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.vn008xw.carbeat.AppExecutors;
import com.example.vn008xw.carbeat.BuildConfig;
import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.db.AccountDao;
import com.example.vn008xw.carbeat.data.db.FavoriteMovieDao;
import com.example.vn008xw.carbeat.data.db.MovieDao;
import com.example.vn008xw.carbeat.data.db.MovieDb;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.utils.DaggerUtils;
import com.example.vn008xw.carbeat.utils.LiveDataCallAdapterFactory;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vn008xw on 6/6/17.
 */
@Module
public class DataModule {

  private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
  private static final int TIMEOUT_LIMIT = 10;
  private static final String KEY_API_KEY = "apikey";
  private static final String KEY_API_ID = "i";


  static OkHttpClient.Builder createOkHttpClient(Application app) {
    //Install a HTTP cache in the application cache directory.
    File cacheDir = new File(app.getCacheDir(), "https");
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
    return DaggerUtils.track(new OkHttpClient.Builder().cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS));
  }

  @NonNull
  private static final String SHARED_PREFRENCES_LOCATION = "com.carbeat.prefs";

  @Provides
  @ApplicationScope
  Gson provideGson() {
    return DaggerUtils.track(new Gson());
  }

  @Provides
  @ApplicationScope
  SharedPreferences provideSharedPreferences(Application app) {
    return DaggerUtils.track(app.getSharedPreferences(SHARED_PREFRENCES_LOCATION, Context.MODE_PRIVATE));
  }

  @Provides
  @ApplicationScope
  RxSharedPreferences provideRxSharedPreferences(SharedPreferences sharedPreferences) {
    return DaggerUtils.track(RxSharedPreferences.create(sharedPreferences));
  }

  @Provides
  @ApplicationScope
  @Named("Endpoint")
  String provideMoviesEndpoint() {
    return BuildConfig.ENDPOINT;
  }

  @Provides
  @ApplicationScope
  HttpLoggingInterceptor provideLoggingInterceptor() {
    if (BuildConfig.DEBUG) {
      return new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    return new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.NONE);
  }

  @Provides
  @ApplicationScope
  OkHttpClient provideOkHttpClient(Application app, HttpLoggingInterceptor httpLoggingInterceptor) {
    return DaggerUtils.track(createOkHttpClient(app).addInterceptor(httpLoggingInterceptor)
            .build());
  }

  @Provides
  @ApplicationScope
  Retrofit provideRetrofitBuilder(OkHttpClient client, Gson gson, @Named("Endpoint") String endpoint) {
    return DaggerUtils.track(new Retrofit.Builder().client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .baseUrl(endpoint).build());
  }

  @Provides
  @ApplicationScope
  MovieService provideMovieService(Retrofit retrofit) {
    return
            DaggerUtils.track(retrofit
                    .create(MovieService.class));
  }

  @Provides
  @ApplicationScope
  MovieDb provideMoviesDb(Application app) {
    return DaggerUtils.track(Room.databaseBuilder(app, MovieDb.class, "movies.db").build());
  }

  @Provides
  @ApplicationScope
  MovieDao provideMovieDao(MovieDb movieDb) {
    return DaggerUtils.track(movieDb.movieDao());
  }

  @Provides
  @ApplicationScope
  FavoriteMovieDao provideFavoriteMovieDao(MovieDb movieDb) {
    return DaggerUtils.track(movieDb.favoriteMovieDao());
  }

  @Provides
  @ApplicationScope
  AccountDao provideAccountDao(@NonNull MovieDb movieDb) {
    return DaggerUtils.track(movieDb.accountDao());
  }

  @Provides
  @ApplicationScope
  AppExecutors provideAppExecutors() {
    return DaggerUtils.track(new AppExecutors());
  }

  @Provides
  @ApplicationScope
  @Named("ApiKey")
  String provideApiKey() {
    return DaggerUtils.track(BuildConfig.API_KEY);
  }
}
