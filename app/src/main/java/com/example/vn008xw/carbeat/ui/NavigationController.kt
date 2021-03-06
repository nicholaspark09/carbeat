package com.example.vn008xw.carbeat.ui

import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.ImageView
import com.example.vn008xw.carbeat.MainActivity
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.base.BaseActivity
import com.example.vn008xw.carbeat.di.ApplicationScope
import com.example.vn008xw.carbeat.ui.account.AccountFragment
import com.example.vn008xw.carbeat.ui.favorites.FavoritesFragment
import com.example.vn008xw.carbeat.ui.movie.MovieActivity
import com.example.vn008xw.carbeat.ui.movies.MoviesFragment
import javax.inject.Inject

/**
 * Created by vn008xw on 6/18/17.
 */
@ApplicationScope
class NavigationController @Inject constructor() {

  val containerId: Int
  var previousScreen: String? = null

  init {
    containerId = R.id.container
  }

  fun setPreviousScreenName(fragment: Fragment) {
    val screenName = fragment::class.java.simpleName
    previousScreen = screenName
  }

  fun navigateToMovies(activity: MainActivity) {
    val screenName = MoviesFragment::class.java.simpleName.toString()
    if (previousScreen == null || !previousScreen.equals(screenName, true)) {
      val moviesFragment = MoviesFragment.newInstance()
      activity.supportFragmentManager
          .beginTransaction()
          .replace(containerId, moviesFragment)
          .commitAllowingStateLoss()
      setPreviousScreenName(moviesFragment)
    }
  }

  fun navigateToFavorites(activity: MainActivity) {
    val favorites = FavoritesFragment.newInstance()
    activity.supportFragmentManager
        .beginTransaction()
        .replace(containerId, favorites)
        .commitAllowingStateLoss()
    setPreviousScreenName(favorites)
  }

  fun navigateToMovie(activity: BaseActivity, movieId: Int, imageView: ImageView?) {
    val intent = Intent(activity, MovieActivity::class.java)
    intent.putExtra(MovieActivity.MOVIE_ID, movieId)
    activity.startActivity(intent)
  }

  fun navigateToAccount(activity: BaseActivity) {
    val account = AccountFragment.newInstance()
    activity.supportFragmentManager
        .beginTransaction()
        .replace(containerId, account)
        .commitAllowingStateLoss()
    setPreviousScreenName(account)
  }
}