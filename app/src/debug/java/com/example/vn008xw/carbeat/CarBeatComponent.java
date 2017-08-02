package com.example.vn008xw.carbeat;

import com.example.vn008xw.carbeat.data.DebugDataModule;
import com.example.vn008xw.carbeat.di.ApplicationScope;
import com.example.vn008xw.carbeat.ui.DebugUiModule;
import com.example.vn008xw.carbeat.ui.DebugView;
import com.example.vn008xw.carbeat.utils.DaggerUtils;

import dagger.Component;

/**
 * Created by vn008xw on 6/7/17.
 */
@ApplicationScope
@Component(modules = {AppModule.class, DebugDataModule.class, DebugUiModule.class})
public interface CarBeatComponent extends AppComponent {

    void inject(DebugView debugView);

    final class Initializer {
        private Initializer(){} //NOP

        static CarBeatComponent init(CarBeatApp carBeatApp) {
            return DaggerUtils.track(DaggerCarBeatComponent.builder().appModule(new AppModule(carBeatApp)).build());
        }
    }
}
