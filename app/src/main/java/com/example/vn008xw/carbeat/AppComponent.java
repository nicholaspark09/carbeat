package com.example.vn008xw.carbeat;

import com.example.vn008xw.carbeat.CarBeatApp;
import com.example.vn008xw.carbeat.MainActivity;

/**
 * Created by vn008xw on 6/6/17.
 */

public interface AppComponent {
    void inject(CarBeatApp carBeatApp);

    void inject(MainActivity mainActivity);
}