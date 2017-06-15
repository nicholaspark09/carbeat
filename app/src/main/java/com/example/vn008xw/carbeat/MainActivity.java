package com.example.vn008xw.carbeat;

import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.example.vn008xw.carbeat.base.BaseActivity;
import com.example.vn008xw.carbeat.databinding.ActivityMainBinding;
import com.example.vn008xw.carbeat.di.DaggerService;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        onCreateView(MainActivity.this);
        if (savedInstanceState == null) {

        } else {

        }

    }

    @Override
    public void inject(@NotNull AppComponent appComponent) {
        appComponent.inject(this);
    }
}
