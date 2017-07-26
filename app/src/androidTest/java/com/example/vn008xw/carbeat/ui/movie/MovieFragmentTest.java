package com.example.vn008xw.carbeat.ui.movie;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.testing.SingleFragmentActivity;
import com.example.vn008xw.carbeat.util.TestUtil;
import com.example.vn008xw.carbeat.util.ViewMatchers;
import com.example.vn008xw.carbeat.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.vn008xw.carbeat.util.ViewMatchers.withDrawable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vn008xw on 7/10/17.
 */
@RunWith(AndroidJUnit4.class)
public class MovieFragmentTest {

  @Rule
  public ActivityTestRule<SingleFragmentActivity> activityRule =
          new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);
  //  @Rule
//  public TaskExecutorWithIdlingResourceRule executorRule =
//          new TaskExecutorWithIdlingResourceRule();
  private MutableLiveData<Resource<FavoriteMovie>> favoriteMovie = new MutableLiveData<>();
  private MutableLiveData<Resource<Movie>> movie = new MutableLiveData<>();
  private MutableLiveData<Resource<ImageResult>> images = new MutableLiveData<>();
  private MovieFragment movieFragment;
  private MovieViewModel viewModel;
  private static final int MOVIE_ID = 9;
  private static final String TITLE = "Ironman";
  private static final String DESC = "Elon Musk is ironman";


  @Before
  public void init() {
    movieFragment = MovieFragment.newInstance(MOVIE_ID);
    viewModel = mock(MovieViewModel.class);
    movieFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

    when(viewModel.getMovie()).thenReturn(movie);
    when(viewModel.getImages()).thenReturn(images);
    when(viewModel.getFavoriteMovie()).thenReturn(favoriteMovie);
    activityRule.getActivity().setFragment(movieFragment);
  }

  @Test
  public void testNull() {
    movie.postValue(Resource.loading(null));
    onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
  }

  @Test
  public void testLoaded() throws InterruptedException {
    final Movie movie = TestUtil.createMovie(MOVIE_ID, TITLE, DESC);
    this.movie.postValue(Resource.success(movie));
    onView(isAssignableFrom(Toolbar.class)).check(matches(ViewMatchers.withToolbarTitle(is(TITLE))));
    onView(withId(R.id.overview)).check(matches(withText(DESC)));
  }

  @Test
  public void testError() throws InterruptedException {

    final String errorMessage = "I'm batman";

    movie.postValue(Resource.loading(null));
    onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
    movie.postValue(Resource.error(errorMessage, null));
    onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    onView(withText(errorMessage)).inRoot(withDecorView(not(is(activityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
  }

  @Test
  public void testLoaded_searchFavorites() throws InterruptedException {
    final String title = "Ironman";
    final String description = "Elon Musk is ironman";
    final Movie movie = TestUtil.createMovie(MOVIE_ID, title, description);
    this.movie.postValue(Resource.success(movie));
    onView(withId(R.id.overview)).check(matches(withText(description)));
    verify(viewModel).setFind(MOVIE_ID, true);
    this.favoriteMovie.postValue(Resource.error("No movie", null));
    onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.ic_star_black)));
  }

  @Test
  public void testLoaded_inFavorites_paintStarWhite() throws InterruptedException {

    final Movie movie = TestUtil.createMovie(MOVIE_ID, TITLE, DESC);
    this.movie.postValue(Resource.success(movie));
    onView(withId(R.id.overview)).check(matches(withText(DESC)));
    verify(viewModel).setFind(MOVIE_ID, true);

    final FavoriteMovie favoriteMovie = TestUtil.createFavoriteMovie(movie);
    this.favoriteMovie.postValue(Resource.success(favoriteMovie));
    onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.ic_star_white)));
  }

  @Test
  public void testLoad_clickFavorite_saveMovie() throws InterruptedException {
    final Movie movie = TestUtil.createMovie(MOVIE_ID, TITLE, DESC);
    this.movie.postValue(Resource.success(movie));
    onView(isAssignableFrom(Toolbar.class)).check(matches(ViewMatchers.withToolbarTitle(is(TITLE))));
    onView(withId(R.id.fab)).perform(click());
    verify(viewModel).saveMovie(movie);
  }

  @Test
  public void clickFab_removeMovie() throws InterruptedException {
    final Movie movie = TestUtil.createMovie(MOVIE_ID, TITLE, DESC);
    this.movie.postValue(Resource.success(movie));

    final FavoriteMovie favoriteMovie = TestUtil.createFavoriteMovie(movie);
    this.favoriteMovie.postValue(Resource.success(favoriteMovie));
    onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.ic_star_white)));
    onView(withId(R.id.fab)).perform(click());
    verify(viewModel).removeMovieFromFavorites();
  }
}
