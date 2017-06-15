package com.example.vn008xw.carbeat.utils

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit


/**
 * Created by vn008xw on 6/14/17.
 */
class RateLimiter<KEY>(timeout: Int, timeUnit: TimeUnit) {
    private var timestamps: ArrayMap<KEY, Long> = ArrayMap()
    private var timeout: Long


    init {
        this.timeout = timeUnit.toMillis(timeout.toLong())
    }

    @Synchronized fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps.get(key)
        val now = now()
        if (lastFetched == null) {
            timestamps.put(key, now)
            return true
        }
        if (now - lastFetched!! > timeout) {
            timestamps.put(key, now)
            return true
        }
        return false
    }

    private fun now(): Long {
        return SystemClock.uptimeMillis()
    }

    @Synchronized fun reset(key: KEY) {
        timestamps.remove(key)
    }
}