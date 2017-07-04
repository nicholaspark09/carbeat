package com.example.vn008xw.carbeat.ui

import android.content.Intent
import android.widget.ImageView
import com.example.vn008xw.carbeat.MainActivity
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.base.BaseActivity
import com.example.vn008xw.carbeat.di.ApplicationScope
import com.example.vn008xw.carbeat.ui.movie.MovieActivity
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

    fun navigateToMovie(activity: BaseActivity, movieId: Int, imageView: ImageView) {
        val intent = Intent(activity, MovieActivity::class.java)
        intent.putExtra(MovieActivity.MOVIE_ID, movieId)
        activity.startActivity(intent)
    }
}