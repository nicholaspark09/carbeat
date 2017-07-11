package com.example.vn008xw.carbeat.ui.favorites

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.example.vn008xw.carbeat.data.repository.FavoriteMovieRepository
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie
import com.example.vn008xw.carbeat.data.vo.Resource
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    @VisibleForTesting
    internal val favoriteMovieRepository: FavoriteMovieRepository) : ViewModel() {

  @VisibleForTesting
  internal val find = MutableLiveData<Boolean>()

  @VisibleForTesting
  internal val favoriteMovieId = MutableLiveData<Int>()

  @VisibleForTesting
  internal val favoriteResult: LiveData<Resource<FavoriteMovie>>

  @VisibleForTesting
  internal val movies: LiveData<Resource<List<FavoriteMovie>>>

  init {
    movies = Transformations.switchMap(find) { goFind ->
      if (goFind == null) AbsentLiveData.create<Resource<List<FavoriteMovie>>>()
      else {
        favoriteMovieRepository.loadFavoriteMovies()
      }
    }
    favoriteResult = Transformations.switchMap(favoriteMovieId) { movieId ->
      if (movieId == null) AbsentLiveData.create<Resource<FavoriteMovie>>()
      else {
        favoriteMovieRepository.loadFavoriteMovie(movieId)
      }
    }
  }

  fun getFavorites() = movies

  fun getFavoriteMovie() = favoriteResult
}