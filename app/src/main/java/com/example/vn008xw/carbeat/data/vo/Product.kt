package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName



/**
 * Created by vn008xw on 6/7/17.
 */
@Entity(primaryKeys = arrayOf("id"))
data class Product constructor(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("price")
        val price: Float,
        @SerializedName("productImage")
        val image: ProductImage)