package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "account")
data class Account constructor(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("pin")
    val pin: String
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int? = null
    set(value) {
      field = value
    }
}