package com.example.vn008xw.carbeat.ui.movies;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.Movie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by vn008xw on 6/26/17.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class MoviesViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private MovieRepository repository;
  private MoviesViewModel viewModel;

  @Before
  public void setup() {
    repository = mock(MovieRepository.class);
    viewModel = new MoviesViewModel(repository);
  }

  @Test
  public void testNotNull() {
    assertThat(viewModel.getMovies(), notNullValue());
    verify(repository, never()).loadMovies(anyInt(), anyString());
  }

  @Test
  public void doesNotFetchWithoutObservers() {
    viewModel.offset.setValue(1);
    verify(repository, never()).loadMovies(anyInt(), anyString());
  }

  @Test
  public void fetchWhenBeingObserver() {
    ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> year = ArgumentCaptor.forClass(String.class);
    viewModel.offset.setValue(2);
    viewModel.getMovies().observeForever(mock(Observer.class));
    verify(repository, times(1)).loadMovies(page.capture(), year.capture());
    assertThat(page.getValue(), is(2));
    assertThat(year.getValue(), is("2017"));
  }

  @Test
  public void refresh_postValue_reload() {
    ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> year = ArgumentCaptor.forClass(String.class);
    viewModel.getMovies().observeForever(mock(Observer.class));
    viewModel.refreshAndReload();
    assertThat(viewModel.offset.getValue(), is(1));
    verify(repository, times(1)).loadMovies(page.capture(), year.capture());
    assertThat(page.getValue(), is(1));
    assertThat(year.getValue(), is("2017"));
  }

  @Test
  public void saveMovie_saveInRepository() {
    Movie movie = new Movie(1, "",
            "", "", "",
            "", 1f, 2,
            false, 1f, "", "");
    viewModel.saveMovie(movie);
    verify(repository, times(1)).saveMovie(movie);
  }
}
