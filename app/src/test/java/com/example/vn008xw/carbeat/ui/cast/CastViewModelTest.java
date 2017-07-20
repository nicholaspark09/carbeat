package com.example.vn008xw.carbeat.ui.cast;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.repository.cast.CastRepository;
import com.example.vn008xw.carbeat.data.vo.Movie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class CastViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
  @Mock
  MovieRepository movieRepository;
  @Mock
  CastRepository castRepository;
  private CastViewModel viewModel;
  private static final Integer MOVIE_ID = 90;
  private static final Movie movie = new Movie(MOVIE_ID, "Blah", "English",
          "Nothing happens in this movie", "Blah", "", 1f,
          2, false, 1.5f, "", "");

  @Before
  public void setupCastViewModelTest() {
    MockitoAnnotations.initMocks(this);
    viewModel = new CastViewModel(castRepository, movieRepository);
  }

  // It should never call the castRepository because the movie id was never set
  @Test
  public void testNull() {
    assertThat(viewModel.getCast(), notNullValue());
    verify(castRepository, never()).getMovieCast(MOVIE_ID);
  }

  @Test
  public void updateMovieId_dontFetchWithoutObservers() {
    viewModel.movieId.setValue(MOVIE_ID);
    assertEquals(MOVIE_ID, viewModel.movieId.getValue());
    verify(castRepository, never()).getMovieCast(MOVIE_ID);
  }

  @Test
  public void updateMovieId_fetchBecauseItsBeingObserved() {
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    viewModel.getCast().observeForever(mock(Observer.class));

    viewModel.movieId.setValue(MOVIE_ID);
    verify(castRepository).getMovieCast(captor.capture());
    assertThat(captor.getValue(), is(MOVIE_ID));
  }

  @Test
  public void findMovie_dontFetch() {
    when(movieRepository.getCachedMovie()).thenReturn(null);
    viewModel.getCast().observeForever(mock(Observer.class));
    viewModel.findMovie();
    assertNull(viewModel.movieId.getValue());
    verifyNoMoreInteractions(castRepository);
  }

  @Test
  public void findMovie_getValue_startFetch() {
    when(movieRepository.getCachedMovie()).thenReturn(movie);
    viewModel.getCast().observeForever(mock(Observer.class));
    viewModel.findMovie();
    assertEquals(MOVIE_ID, viewModel.movieId.getValue());
    verify(castRepository).getMovieCast(MOVIE_ID);

  }
}
