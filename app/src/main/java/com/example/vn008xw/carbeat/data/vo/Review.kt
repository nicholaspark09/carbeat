package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "review", primaryKeys = arrayOf("id"))
data class Review constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("url")
    val url: String
)