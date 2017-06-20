package com.example.vn008xw.carbeat;

import android.app.Application;

import com.example.vn008xw.carbeat.CarBeatApp;
import com.example.vn008xw.carbeat.data.DataModule;
import com.example.vn008xw.carbeat.data.movie.MovieRepository;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.ui.UiModule;
import com.example.vn008xw.carbeat.ui.movies.MoviesViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vn008xw on 6/6/17.
 */
@Module(includes = {DataModule.class, UiModule.class})
public final class AppModule {

    private final CarBeatApp app;

    AppModule(CarBeatApp app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return app;
    }
}
