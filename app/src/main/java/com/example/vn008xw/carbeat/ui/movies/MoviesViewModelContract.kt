package com.example.vn008xw.carbeat.ui.movies

import android.arch.lifecycle.LiveData
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.data.vo.Resource
import com.example.vn008xw.carbeat.data.vo.SearchResult

/**
 * Created by vn008xw on 6/16/17.
 */
interface MoviesViewModelContract {

    fun loadMore()
    fun getMovies() : LiveData<Resource<List<SearchResult>>>
    fun refreshAndReload()
}