package com.example.vn008xw.carbeat.ui.review

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.example.vn008xw.carbeat.data.repository.ReviewRepository
import com.example.vn008xw.carbeat.data.vo.*
import javax.inject.Inject


class ReviewViewModel @Inject
    constructor(@VisibleForTesting val reviewRepository: ReviewRepository): ViewModel() {

    @VisibleForTesting
    internal var movieId: Int? = null
    @VisibleForTesting
    internal val page = MutableLiveData<Int>()
    @VisibleForTesting
    internal val reviewResults: LiveData<Resource<List<ReviewResult>>>

    init {
        reviewResults = Transformations.switchMap(page) { number ->
            if (number == null || movieId == null) AbsentLiveData.create<Resource<List<ReviewResult>>>()
            else {
                reviewRepository.loadReviews(movieId!!, number)
            }
        }
    }

    fun getReviews(): LiveData<Resource<List<ReviewResult>>> {
        return reviewResults
    }

    fun setMovieId(id: Int) {
        movieId = id
        page.value = 0
    }

}