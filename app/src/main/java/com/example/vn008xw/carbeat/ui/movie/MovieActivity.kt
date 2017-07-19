package com.example.vn008xw.carbeat.ui.movie

import android.os.Bundle
import android.view.MenuItem
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.base.BaseActivity
import com.example.vn008xw.carbeat.utils.ActivityUtil


class MovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val movieFragment = findOrCreateFragment()
        ActivityUtil.replaceFragmentSlideIn(supportFragmentManager, movieFragment, R.id.contentFrame)
    }

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun findOrCreateFragment(): MovieFragment {
        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        var movieFragment: MovieFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame)?.let { it as MovieFragment }
        return movieFragment ?: MovieFragment.newInstance(movieId)
    }

    companion object {
        val MOVIE_ID = "MOVIE_ID"
    }
}
