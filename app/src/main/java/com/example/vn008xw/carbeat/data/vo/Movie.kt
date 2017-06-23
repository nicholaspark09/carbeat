package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import java.sql.Date

/**
 * Created by vn008xw on 6/11/17.
 */
@Entity(tableName = "movie", primaryKeys = arrayOf("id"))
data class Movie constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("popularity")
    val popularity: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("genre_ids")
    val genres: String,
    @SerializedName("release_date")
    val releaseDate: String
)