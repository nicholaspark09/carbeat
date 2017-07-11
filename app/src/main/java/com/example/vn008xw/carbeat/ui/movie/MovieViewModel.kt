package com.example.vn008xw.carbeat.ui.movie

import android.arch.lifecycle.*
import android.support.annotation.VisibleForTesting
import android.util.Log
import com.example.vn008xw.carbeat.data.repository.FavoriteMovieRepository
import com.example.vn008xw.carbeat.data.repository.ImageRepository
import com.example.vn008xw.carbeat.data.repository.MovieRepository
import com.example.vn008xw.carbeat.data.vo.*
import javax.inject.Inject


class MovieViewModel @Inject
constructor(@VisibleForTesting
            internal val movieRepository: MovieRepository,
            @VisibleForTesting
            internal val imageRepository: ImageRepository,
            @VisibleForTesting
            val favoriteMovieRepository: FavoriteMovieRepository) : ViewModel() {

  @VisibleForTesting
  internal val movieId = MutableLiveData<Int>()
  @VisibleForTesting
  internal val movieResult: LiveData<Resource<Movie>>
  @VisibleForTesting
  internal val images: LiveData<Resource<ImageResult>>
  @VisibleForTesting
  internal val favoriteMovie: LiveData<Resource<FavoriteMovie>>
  @VisibleForTesting
  internal val saved = MutableLiveData<Pair<Int, Boolean>>()

  init {
    movieResult = Transformations.switchMap(movieId) { id ->
      if (id == null) AbsentLiveData.create<Resource<Movie>>()
      else {
        movieRepository.loadMovie(id)
      }
    }
    images = Transformations.switchMap(movieId) { id ->
      if (id == null) AbsentLiveData.create<Resource<ImageResult>>()
      else {
        imageRepository.loadImages(id)
      }
    }
    favoriteMovie = Transformations.switchMap(saved) { id ->
      if (id == null) AbsentLiveData.create<Resource<FavoriteMovie>>()
      else {
        favoriteMovieRepository.loadFavoriteMovie(id.first)
      }
    }
  }

  fun setFind(id: Int, find: Boolean) {
    saved.value = Pair(id, find)
  }

  fun setMovieId(id: Int) {
    movieId.value = id
  }

  fun getMovie() = movieResult

  fun getImages() = images

  fun getFavoriteMovie() = favoriteMovie

  fun saveMovie(movie: Movie) {
    val favoriteMovie = FavoriteMovie(movie.title, movie)
    favoriteMovieRepository.saveFavoriteMovie(favoriteMovie)
    saved.value = Pair(movie.id, true)
  }
}