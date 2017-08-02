package com.example.vn008xw.carbeat.ui.shows;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.vn008xw.carbeat.data.repository.tv.TvRepository;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.data.vo.ShowResult;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ShowsViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
  @Mock
  private TvRepository repository;
  @Mock
  Observer observer;
  private ShowsViewModel viewModel;

  @Before
  public void setupShowsViewModelTest() {
    MockitoAnnotations.initMocks(this);

    viewModel = new ShowsViewModel(repository);
  }

  @Test
  public void testNotNull() {
    assertThat(viewModel.showStream(), notNullValue());
    // The page wasn't updated, so it shouldn't make a call to the repository
    verify(repository, never()).getPopularShows(anyInt());
  }

  @Test
  public void doesNotFetchWhenNotObserved() {
    viewModel.page.setValue(1);
    verify(repository, never()).getPopularShows(1);
  }

  @Test
  public void fetchWhenBeingObserved() {
    final int theNewPageValue = 2;
    ArgumentCaptor<Integer> paged = ArgumentCaptor.forClass(Integer.class);

    final ShowResult shows = new ShowResult(2, Collections.emptyList(), 0, 0);
    final MutableLiveData<Resource<ShowResult>> result = new MutableLiveData<>();

    when(repository.getPopularShows(theNewPageValue)).thenReturn(result);

    viewModel.page.setValue(theNewPageValue);
    viewModel.showStream().observeForever(observer);
    verify(repository, times(1)).getPopularShows(paged.capture());
    assertThat(paged.getValue(), is(theNewPageValue));

    final Resource<ShowResult> success = Resource.success(shows);

    result.setValue(success);
    verify(observer).onChanged(success);
  }

  @Test
  public void loadMore_updatePageValue() {
    viewModel.loadMore();
    viewModel.loadMore();
    assertThat(viewModel.page.getValue(), is(3));
  }

  @Test
  public void refresh_refreshRepository() {
    viewModel.refresh();
    verify(repository, times(1)).refresh();
    assertThat(viewModel.page.getValue(), is(1));
  }
}
