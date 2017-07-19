package com.example.vn008xw.carbeat.data.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.vn008xw.carbeat.data.api.MovieService;
import com.example.vn008xw.carbeat.data.vo.Movie;
import com.example.vn008xw.carbeat.data.vo.Resource;
import com.example.vn008xw.carbeat.utils.InstantAppExecutors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by vn008xw on 7/13/17.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class MovieRepositoryTest {

  private MovieService movieService;
  private MovieRepository repository;
  private Movie movie;
  private static final int ID = 92;
  private static final String TITLE = "Eat It";
  private MutableLiveData<Resource<Movie>> liveMovie = new MutableLiveData<>();

  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Before
  public void setupMovieRepositoryTest() {
    movie = new Movie(ID, TITLE, "en",
            "", TITLE, "", 1f, 100,
            false, 1f, "", "");
    movieService = mock(MovieService.class);

    repository = new MovieRepository(new InstantAppExecutors(), movieService);
  }

  @Test
  public void saveMovie_updateCache() {
    repository.saveMovie(movie);
    assertEquals(movie, repository.cachedMovie);
  }

  @Test
  public void setRefresh_setCacheIsDirty() {
    repository.setRefresh();
    assertEquals(true, repository.cacheIsDirty);
  }

  @Test
  public void loadMovie_cacheEmpty_showError() {
    Observer<Resource<Movie>> observer = mock(Observer.class);
    repository.loadMovie(ID).observeForever(observer);
    verify(observer).onChanged(Resource.error("Sorry, the movie wasn't found", null));
  }


}
