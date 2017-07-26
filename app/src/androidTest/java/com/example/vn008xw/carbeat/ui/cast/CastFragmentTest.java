package com.example.vn008xw.carbeat.ui.cast;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vn008xw.carbeat.R;
import com.example.vn008xw.carbeat.data.vo.Cast;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.testing.SingleFragmentActivity;
import com.example.vn008xw.carbeat.util.RecyclerViewMatcher;
import com.example.vn008xw.carbeat.util.TestUtil;
import com.example.vn008xw.carbeat.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

/**
 * Created by vn008xw on 7/20/17.
 */
@RunWith(AndroidJUnit4.class)
public class CastFragmentTest {

  @Rule
  public ActivityTestRule<SingleFragmentActivity> activityRule =
          new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);
//  @Rule
//  public TaskExecutorWithIdlingResourceRule executorRule =
//          new TaskExecutorWithIdlingResourceRule();
  private MutableLiveData<Resource<List<Cast>>> cast = new MutableLiveData<>();
  private CastFragment fragment;
  private ImmutableList<Cast> testCast;
  @Mock
  CastViewModel viewModel;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    fragment = CastFragment.newInstance();
    fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

    testCast = TestUtil.createCastList(10);
    when(viewModel.getCast()).thenReturn(cast);
    activityRule.getActivity().setFragment(fragment);
  }

  @Test
  public void testNull() {
    cast.postValue(Resource.loading(null));
    onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
  }

  @Test
  public void testLoaded() throws InterruptedException {
    cast.postValue(Resource.success(testCast));
    onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    onView(listMatcher().atPosition(0))
            .check(matches(hasDescendant(withText("jack"))));
  }

  @NonNull
  private RecyclerViewMatcher listMatcher() {
    return new RecyclerViewMatcher(R.id.recycler_view);
  }
}
