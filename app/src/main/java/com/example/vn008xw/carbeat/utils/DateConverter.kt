package com.example.vn008xw.carbeat.utils

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    companion object {
        @TypeConverter
        fun Long.toDate(): Date? = when(this) {
            null -> null
            else -> Date(this)
        }

        @TypeConverter
        fun Date.toLong(): Long? = when(this) {
            null -> null
            else -> this.time
        }
    }
}

