package com.example.vn008xw.carbeat.ui

import android.widget.ImageView
import com.example.vn008xw.carbeat.MainActivity
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.di.ApplicationScope
import com.example.vn008xw.carbeat.ui.movie.MovieFragment
import com.example.vn008xw.carbeat.ui.movies.MoviesFragment
import javax.inject.Inject

/**
 * Created by vn008xw on 6/18/17.
 */
@ApplicationScope
class NavigationController @Inject constructor() {

    val containerId: Int

    init {
        containerId = R.id.container
    }

    fun navigateToMovies(activity: MainActivity) {
        val moviesFragment = MoviesFragment.newInstance()
        activity.supportFragmentManager
                .beginTransaction()
                .replace(containerId, moviesFragment)
                .commitAllowingStateLoss()
    }

    fun navigateToMovie(activity: MainActivity, movieId: Int, imageView: ImageView) {
        val movieFragment = MovieFragment.newInstance(movieId)
        activity.supportFragmentManager
                .beginTransaction()
                .addSharedElement(imageView, activity.applicationContext.getString(R.string.list_image_transition))
                .replace(containerId, movieFragment)
                .addToBackStack(movieFragment.javaClass.simpleName)
                .commitAllowingStateLoss()

    }
}