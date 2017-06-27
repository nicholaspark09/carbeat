package com.example.vn008xw.carbeat.utils

/**
 * Created by vn008xw on 6/23/17.
 */
val basePath = "https://image.tmdb.org/t/p"
val smallUrl = "/w185/"
val largeUrl = "/w500/"

class ImageUtil {

    companion object {
        @JvmStatic
        fun getLittleImage(posterPath: String?) = posterPath?.getSmallImageUrl()
    }
}

fun String.getSmallImageUrl() = basePath + smallUrl + this
fun String.getLargeImageUrl() = basePath + largeUrl + this