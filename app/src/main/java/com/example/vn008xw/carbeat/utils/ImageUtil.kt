package com.example.vn008xw.carbeat.utils

/**
 * Created by vn008xw on 6/23/17.
 */
val basePath = "https://image.tmdb.org/t/p"
val smallUrl = "/w185/"
val largeUrl = "/w500/"

inline fun String.getSmallImageUrl() = basePath + smallUrl + this