package com.example.vn008xw.carbeat.data.api

import android.arch.lifecycle.LiveData
import com.example.vn008xw.carbeat.data.vo.ApiResponse
import com.example.vn008xw.carbeat.data.vo.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by vn008xw on 6/11/17.
 */
interface MovieService {

    @GET("/3/discover/movie")
    fun discoverByYear(@Query("api_key") apiKey: String,
                       @Query("page") page: Int,
                       @Query("primary_release_year") year: String): LiveData<ApiResponse<SearchResult>>
}