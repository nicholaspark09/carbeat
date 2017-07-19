package com.example.vn008xw.carbeat.utils;

import com.example.vn008xw.carbeat.AppExecutors;

import java.util.concurrent.Executor;

/**
 * Created by vn008xw on 7/13/17.
 */

public class InstantAppExecutors extends AppExecutors {

  private static Executor instant = command -> command.run();

  public InstantAppExecutors() {
    super(instant, instant, instant);
  }
}
