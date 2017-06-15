package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Created by vn008xw on 6/10/17.
 */
@Entity(primaryKeys = arrayOf("imageUrl"))
data class ProductImage constructor(
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("thumbUrl")
        val thumbUrl: String,
        @SerializedName("imageWidth")
        val imageWidth: Int,
        @SerializedName("imageHeight")
        val imageHeight: Int)