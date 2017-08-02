package com.example.vn008xw.carbeat.data.api

import android.arch.lifecycle.LiveData
import com.example.vn008xw.carbeat.data.vo.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
  @GET("/3/discover/movie")
  fun discoverByYear(@Query("api_key") apiKey: String,
                     @Query("page") page: Int,
                     @Query("primary_release_year") year: String): LiveData<ApiResponse<SearchResult>>

  @GET("/3/movie/{movieId}/reviews")
  fun getReviews(@Path("movieId") movieId: Int,
                 @Query("api_key") apiKey: String,
                 @Query("page") page: Int): LiveData<ApiResponse<ReviewResult>>

  @GET("/3/movie/{movieId}/images")
  fun getImages(@Path("movieId") movieId: Int,
                @Query("api_key") apiKey: String): LiveData<ApiResponse<ImageResult>>

  @GET("/3/movie/{movieId}/credits")
  fun getCredits(@Path("movieId") movieId: Int,
                 @Query("api_key") apiKey: String): LiveData<ApiResponse<MovieCastResult>>

  @GET("/3/discover/tv")
  fun getPopularTvShows(@Query("api_key") apiKey: String,
                        @Query("page") page: Int): LiveData<ApiResponse<ShowResult>>
}