package com.example.vn008xw.carbeat.data.api

import android.arch.lifecycle.LiveData
import com.example.vn008xw.carbeat.data.vo.ApiResponse
import com.example.vn008xw.carbeat.data.vo.Movie
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by vn008xw on 6/11/17.
 */
interface MovieService {
    @GET("")
    fun query(@QueryMap options: Map<String, String>): LiveData<ApiResponse<List<Movie>>>

    @GET("")
    fun findMovie(@QueryMap options: Map<String, String>): LiveData<ApiResponse<Movie>>
}