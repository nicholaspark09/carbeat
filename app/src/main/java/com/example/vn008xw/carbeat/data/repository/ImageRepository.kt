package com.example.vn008xw.carbeat.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.example.vn008xw.carbeat.AppExecutors
import com.example.vn008xw.carbeat.BuildConfig
import com.example.vn008xw.carbeat.data.api.MovieService
import com.example.vn008xw.carbeat.data.vo.ApiResponse
import com.example.vn008xw.carbeat.data.vo.ImageResult
import com.example.vn008xw.carbeat.data.vo.Resource
import com.example.vn008xw.carbeat.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
open class ImageRepository @Inject constructor(private val movieService: MovieService,
                                          private val appExecutors: AppExecutors) {
  var movieId: Int? = null
  var imageResult: ImageResult? = null

  fun loadImages(movieId: Int): LiveData<Resource<ImageResult>> {
    val result = MediatorLiveData<Resource<ImageResult>>()
    if (this.movieId != null && this.movieId == movieId && imageResult != null) {
      result.value = Resource.success(imageResult)
    } else {
      this.movieId = movieId

      // Load from the network
      val apiResponse = createNetworkCall(movieId)
      result.addSource(apiResponse, { data: ApiResponse<ImageResult>? ->
        {
          result.removeSource(apiResponse)
        }.run {
          if (data != null && data.isSuccessful()) {
            imageResult = data.body
            result.value = Resource.success(data.body)
          } else if (data != null) {
            result.value = Resource.error(data.errorMessage, null)
          } else {
            result.value = Resource.error("No response", null)
          }
        }
      })
    }
    return result
  }

  fun createNetworkCall(movieId: Int): LiveData<ApiResponse<ImageResult>> {
    return movieService.getImages(movieId, BuildConfig.API_KEY)
  }
}