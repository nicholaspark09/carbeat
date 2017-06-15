package com.example.vn008xw.carbeat.utils

import android.support.v4.util.Pair


/**
 * Created by vn008xw on 6/14/17.
 */

fun <K,V> HashMap<K,V>.addToMap(key: K, value: V): HashMap<K, V>  = HashMap(this).apply {
    put(key, value)
}
