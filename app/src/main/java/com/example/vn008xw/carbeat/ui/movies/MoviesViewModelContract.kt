package com.example.vn008xw.carbeat.ui.movies

import android.arch.lifecycle.LiveData
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.data.vo.Resource

/**
 * Created by vn008xw on 6/16/17.
 */
interface MoviesViewModelContract {

    fun start()
    fun loadMovies(offset: Int?, year: String?): LiveData<Resource<List<Movie>>>
}