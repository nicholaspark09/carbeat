package com.example.vn008xw.carbeat.ui.movies;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vn008xw.carbeat.testing.SingleFragmentActivity;
import com.example.vn008xw.carbeat.util.TaskExecutorWithIdlingResourceRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by vn008xw on 7/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class MoviesFragmentTest {

  @Rule
  public ActivityTestRule<SingleFragmentActivity> activityRule =
          new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);
  @Rule
  public TaskExecutorWithIdlingResourceRule executorRule =
          new TaskExecutorWithIdlingResourceRule();


}
