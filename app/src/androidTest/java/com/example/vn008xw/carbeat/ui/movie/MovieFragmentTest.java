package com.example.vn008xw.carbeat.ui.movie;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vn008xw.carbeat.AppComponent;
import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.TestApp;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.testing.SingleFragmentActivity;
import com.example.vn008xw.carbeat.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import view.FragmentBindingAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
  private MutableLiveData<Resource<ImageResult>> images = new MutableLiveData<>();
  private MovieFragment movieFragment;
  private MovieViewModel viewModel;
  private FragmentBindingAdapter bindingAdapter;
  private static final int MOVIE_ID = 9;


  @Before
  public void init() {
    movieFragment = MovieFragment.newInstance(MOVIE_ID);
    viewModel = mock(MovieViewModel.class);
    bindingAdapter = mock(FragmentBindingAdapter.class);
    movieFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

    when(viewModel.getMovie()).thenReturn(movie);
    when(viewModel.getImages()).thenReturn(images);
    when(viewModel.getFavoriteMovie()).thenReturn(favoriteMovie);
    activityRule.getActivity().setFragment(movieFragment);
  }

  @Test
  public void testLoading() {
    movie.postValue(Resource.loading(null));
    onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
  }
}
