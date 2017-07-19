package com.example.vn008xw.carbeat.ui.movie;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.vn008xw.carbeat.data.repository.FavoriteMovieRepository;
import com.example.vn008xw.carbeat.data.repository.ImageRepository;
import com.example.vn008xw.carbeat.data.repository.MovieRepository;
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie;
import com.example.vn008xw.carbeat.data.vo.ImageResult;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by vn008xw on 7/10/17.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class MovieViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private MovieRepository movieRepository;
  private ImageRepository imageRepository;
  private FavoriteMovieRepository favoriteMovieRepository;
  private MovieViewModel movieViewModel;
  private static final Integer MOVIE_ID = 90;
  private static final int MOVIE_ALT_ID = 101;
  private static final Movie movie = new Movie(MOVIE_ID, "Blah", "English",
          "Nothing happens in this movie", "Blah", "", 1f,
          2, false, 1.5f, "", "");

  @Before
  public void setupMovieViewModelTest() {
    movieRepository = mock(MovieRepository.class);
    imageRepository = mock(ImageRepository.class);
    favoriteMovieRepository = mock(FavoriteMovieRepository.class);

    movieViewModel = new MovieViewModel(movieRepository, imageRepository,
            favoriteMovieRepository);
  }

  @Test
  public void testNull() {
    assertThat(movieViewModel.getMovie(), notNullValue());
    // With no observers, the repository should never make the load call
    verify(movieRepository, never()).loadMovie(MOVIE_ID);
  }

  @Test
  public void updateId_dontFetchWithoutObservers() {
    movieViewModel.setMovieId(MOVIE_ID);
    verify(movieRepository, never()).loadMovie(MOVIE_ID);
    verify(favoriteMovieRepository, never()).loadFavoriteMovie(MOVIE_ID);
  }

  @Test
  public void updateId_fetchWhenObserved() {
    ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);
    movieViewModel.getMovie().observeForever(mock(Observer.class));

    movieViewModel.setMovieId(MOVIE_ID);
    movieViewModel.setMovieId(MOVIE_ALT_ID);
    verify(movieRepository, times(2)).loadMovie(id.capture());
    assertThat(id.getAllValues(), is(Arrays.asList(MOVIE_ID, MOVIE_ALT_ID)));
  }

  @Test
  public void updateSaved_dontFetchWithoutObserver() {
    movieViewModel.setFind(MOVIE_ID, true);
    verify(favoriteMovieRepository, never()).loadFavoriteMovie(MOVIE_ID);
  }

  @Test
  public void updateSaved_fetchWhenObserved() {
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    movieViewModel.getFavoriteMovie().observeForever(mock(Observer.class));
    movieViewModel.setFind(MOVIE_ID, true);
    verify(favoriteMovieRepository, times(1)).loadFavoriteMovie(captor.capture());
    assertEquals(MOVIE_ID, captor.getValue());
  }

  @Test
  public void saveMovie_notObserved_dontRefetch() {
    movieViewModel.saveMovie(movie);
    final FavoriteMovie favoriteMovie = new FavoriteMovie(movie.getTitle(), movie);
    verify(favoriteMovieRepository).saveFavoriteMovie(favoriteMovie);
    verifyNoMoreInteractions(favoriteMovieRepository);
  }

  @Test
  public void saveMovie_refetchWhenObserved() {
    ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
    final Movie testMovie = new Movie(2, "", "",
            "", "", "", 1f, 2,
            false, 1f, "", "");
    final FavoriteMovie favoriteMovie = new FavoriteMovie(testMovie.getTitle(), testMovie);
    movieViewModel.getFavoriteMovie().observeForever(mock(Observer.class));
    movieViewModel.saveMovie(testMovie);
    verify(favoriteMovieRepository).saveFavoriteMovie(favoriteMovie);
    verify(favoriteMovieRepository).loadFavoriteMovie(idCaptor.capture());
    assertThat(idCaptor.getValue(), is(2));
  }

  @Test
  public void deleteFavoriteMovie() {
    ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
    final Movie testMovie = new Movie(2, "", "",
            "", "", "", 1f, 2,
            false, 1f, "", "");
    final FavoriteMovie favoriteMovie = new FavoriteMovie(testMovie.getTitle(), testMovie);
    MutableLiveData<Resource<FavoriteMovie>> foo = new MutableLiveData<>();
    final Resource<FavoriteMovie> fooValue = Resource.success(favoriteMovie);
    when(favoriteMovieRepository.loadFavoriteMovie(MOVIE_ID)).thenReturn(foo);

    Observer<Resource<FavoriteMovie>> observer = mock(Observer.class);
    movieViewModel.favoriteMovie.observeForever(observer);

    movieViewModel.setFind(MOVIE_ID, true);

    foo.setValue(fooValue);
    verify(observer).onChanged(fooValue);

    movieViewModel.removeMovieFromFavorites();
    verify(favoriteMovieRepository).deleteFavoriteMovie(fooValue.data);
  }

  @Test
  public void loadImages_returnAbsent() {
    ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
    Observer<Resource<ImageResult>> observer = mock(Observer.class);
    movieViewModel.images.observeForever(observer);
    movieViewModel.setMovieId(null);
    verify(observer).onChanged(null);
  }
}
