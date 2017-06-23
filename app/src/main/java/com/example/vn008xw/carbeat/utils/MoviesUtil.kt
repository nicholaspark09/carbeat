package com.example.vn008xw.carbeat.utils

import com.example.vn008xw.carbeat.data.vo.Resource
import com.example.vn008xw.carbeat.data.vo.SearchResult

/**
 * Created by vn008xw on 6/21/17.
 */
fun Resource<SearchResult>.extractMovies() = this.data?.results?.asList().orEmpty()