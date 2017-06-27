package com.example.vn008xw.carbeat.utils

import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.data.vo.Resource
import com.example.vn008xw.carbeat.data.vo.SearchResult

/**
 * Created by vn008xw on 6/21/17.
 */
fun Resource<List<SearchResult>>.extractMovies(): List<Movie> {
    val movies = mutableListOf<Movie>()
    this.data?.forEach {
        movies.addAll(it.results)
    }
    return movies
}