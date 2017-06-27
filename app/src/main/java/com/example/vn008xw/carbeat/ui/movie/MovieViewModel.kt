package com.example.vn008xw.carbeat.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.example.vn008xw.carbeat.data.repository.MovieRepository
import com.example.vn008xw.carbeat.data.vo.AbsentLiveData
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.data.vo.Resource
import javax.inject.Inject


class MovieViewModel @Inject
constructor(@VisibleForTesting
            internal val movieRepository: MovieRepository) : ViewModel() {

    @VisibleForTesting
    internal val movieId = MutableLiveData<Int>()
    @VisibleForTesting
    internal val movieResult: LiveData<Resource<Movie>>

    init {
        movieResult = Transformations.switchMap(movieId) { id ->
            if (id == null) AbsentLiveData.create<Resource<Movie>>()
            else {
                movieRepository.loadMovie(id)
            }
        }
    }

    fun getMovie(): LiveData<Resource<Movie>> {
        return movieResult
    }
}