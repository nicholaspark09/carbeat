package com.example.vn008xw.carbeat.data.vo

import com.google.gson.annotations.SerializedName


data class ReviewResult constructor(
        @SerializedName("id")
        val movieId: Int,
        @SerializedName("page")
        val page: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        @SerializedName("total_results")
        val totalResults: Int,
        @SerializedName("results")
        val results: Array<Review>
)
