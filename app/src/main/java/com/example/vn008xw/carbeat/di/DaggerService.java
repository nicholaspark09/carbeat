package com.example.vn008xw.carbeat.di;

import android.content.Context;

import com.example.vn008xw.carbeat.CarBeatComponent;


/**
 * Created by vn008xw on 6/6/17.
 */

public final class DaggerService {
    public static final String SERVICE_NAME = DaggerService.class.getName();

    private DaggerService() {
    } //NOP

    @SuppressWarnings("WrongConstant")
    public static CarBeatComponent getAppComponent(Context context) {
        return (CarBeatComponent) context.getApplicationContext().getSystemService(SERVICE_NAME);
    }
}
