package com.example.vn008xw.carbeat.base

import android.app.Activity
import android.arch.lifecycle.LifecycleFragment
import android.content.Context
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.di.DaggerService

/**
 * Created by vn008xw on 6/16/17.
 */
abstract class BaseView : LifecycleFragment() {

    var injected: Boolean = false

    abstract fun inject(appComponent: AppComponent)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (!injected) {
            inject(DaggerService.getAppComponent(context))
            injected = false
        }
    }
}