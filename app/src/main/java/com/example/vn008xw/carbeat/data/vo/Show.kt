package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "show", primaryKeys = arrayOf("id"))
data class Show constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("genre_ids")
    val genres: List<Int>,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("popularity")
    val popularity: Float,
    @SerializedName("poster_path")
    val posterPath: String
)