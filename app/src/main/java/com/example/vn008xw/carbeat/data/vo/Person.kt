package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName="person")
data class Person constructor (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)