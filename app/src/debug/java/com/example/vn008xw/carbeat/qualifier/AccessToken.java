package com.example.vn008xw.carbeat.qualifier;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by nicholaspark on 10/31/16.
 */
@Qualifier
@Retention(RUNTIME)
public @interface AccessToken {
}
