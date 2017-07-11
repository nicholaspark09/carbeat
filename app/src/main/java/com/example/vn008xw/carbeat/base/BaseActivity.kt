package com.example.vn008xw.carbeat.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.di.DaggerService

/**
 * Created by vn008xw on 6/14/17.
 */
abstract class BaseActivity : AppCompatActivity() {

  var injected: Boolean = false

  abstract fun inject(appComponent: AppComponent)

  fun onCreateView(context: Context) {
    if (!injected) {
      inject(DaggerService.getAppComponent(context))
      injected = false
    }
  }
}