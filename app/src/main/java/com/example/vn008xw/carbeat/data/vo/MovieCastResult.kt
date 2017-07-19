package com.example.vn008xw.carbeat.data.vo

import com.google.gson.annotations.SerializedName

data class MovieCastResult constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val cast: List<Cast>
)