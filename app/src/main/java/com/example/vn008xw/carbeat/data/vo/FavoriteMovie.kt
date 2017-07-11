package com.example.vn008xw.carbeat.data.vo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favoritemovie", indices = arrayOf(index@ Index("title"), index@ Index("id")))
data class FavoriteMovie constructor(
    val title: String,
    @Embedded(prefix = "movie_")
    val movie: Movie
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int? = null
    set(value) {
      field = value
    }

}