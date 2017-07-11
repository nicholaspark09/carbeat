package com.example.vn008xw.carbeat.ui.movie;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.testing.SingleFragmentActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by vn008xw on 7/10/17.
 */
@RunWith(AndroidJUnit4.class)
public class MovieFragmentTest {

  @Rule
  public ActivityTestRule<SingleFragmentActivity> activityRule =
          new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

  private MutableLiveData<Resource<FavoriteMovie>> favoriteMovie = new MutableLiveData<>();
  private MutableLiveData<Resource<Movie>> movie = new MutableLiveData<>();
  private MovieFragment movieFragment;
  private MovieViewModel movieViewModel;


}
