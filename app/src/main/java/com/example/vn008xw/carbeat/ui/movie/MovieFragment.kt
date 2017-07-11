package com.example.vn008xw.carbeat.ui.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.base.BaseView
import com.example.vn008xw.carbeat.data.vo.Poster
import com.example.vn008xw.carbeat.data.vo.Status.ERROR
import com.example.vn008xw.carbeat.data.vo.Status.SUCCESS
import com.example.vn008xw.carbeat.databinding.FragmentMovieBinding
import com.example.vn008xw.carbeat.utils.AutoClearedValue
import com.example.vn008xw.carbeat.utils.getDrawable
import com.example.vn008xw.carbeat.utils.getLargeImageUrl
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MovieFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : BaseView() {
  @Inject lateinit var factory: ViewModelProvider.Factory
  private var movieId: Int? = null
  private lateinit var viewModel: MovieViewModel
  private lateinit var binding: AutoClearedValue<FragmentMovieBinding>
  private lateinit var movieImageAdapter: AutoClearedValue<MovieImageListAdapter>

  override fun inject(appComponent: AppComponent) = appComponent.inject(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      movieId = arguments.getInt(MOVIE_ID)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?) =
      FragmentMovieBinding.inflate(inflater, container, false).apply {
        saved = false
        binding = AutoClearedValue(this@MovieFragment, this)
        setHasOptionsMenu(true)
      }.root

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
    viewModel.getMovie().observe(this@MovieFragment, Observer {
      when (it?.status) {
        SUCCESS -> {
          Log.d("MovieFragment", "You got a movie back: " + it?.data?.title)
          binding.get()?.movie = it.data
          binding.get()?.imageUrl = it.data?.posterPath?.getLargeImageUrl()
          if (it.data != null)
            viewModel.setFind(it.data.id, true)
        }
        ERROR -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
      }
    })

    val movieImageListAdapter = MovieImageListAdapter(object : MovieImageListAdapter.MovieImageCallback {
      override fun onClick(poster: Poster) {
        Log.d(TAG, "You clicked $poster")
      }
    })

    binding.get().imageRecyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    binding.get().imageRecyclerView?.adapter = movieImageListAdapter
    movieImageAdapter = AutoClearedValue(this, movieImageListAdapter)

    viewModel.getImages().observe(this@MovieFragment, Observer {
      when (it?.status) {
        SUCCESS -> {
          val length = it?.data?.posters?.size ?: 0
          Log.d("MovieFragment", "You got images back length $length")
          movieImageAdapter.get().replace(it?.data?.posters ?: Collections.emptyList())
        }
        ERROR -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
      }
    })

    viewModel.getFavoriteMovie().observe(this@MovieFragment, Observer {
      when (it?.status) {
        ERROR -> {
          binding.get().fab.setImageDrawable(getDrawable(context, R.drawable.ic_star_black))
          binding.get().saved = false
        }
        SUCCESS -> {
          Log.d(TAG, "You have already saved it");
          binding.get().fab.setImageDrawable(getDrawable(context, R.drawable.ic_star_white))
          binding.get().saved = true
        }
      }
    })

    viewModel.setMovieId(movieId!!)
    setupToolbar()
    setupBottomSheetListener()
    setupListener()
  }

  fun setupToolbar() {
    binding.get().toolbar.apply {
      val compatActivity = activity.let { it as AppCompatActivity }
      compatActivity.setSupportActionBar(this)
      compatActivity.supportActionBar.apply {
        Log.d(MovieFragment::class.java.simpleName, "Displaying the home button")
        this?.setDisplayHomeAsUpEnabled(true)
        this?.setDisplayShowHomeEnabled(true)
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    Log.d(MovieFragment::class.java.simpleName, "Detected a click")
    when (item?.itemId) {
      android.R.id.home -> {
        Log.d(MovieFragment::class.java.simpleName, "Detected a back click")
        activity.onBackPressed()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  fun setupBottomSheetListener() {
    binding.get().apply {
      val behavior = BottomSheetBehavior.from<View>(bottomSheet)
      behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
          when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
              if (movieId != null) {

              }
            }
            BottomSheetBehavior.STATE_HIDDEN -> {

            }
          }
        }

      })
    }
  }

  fun setupListener() {
    binding.get().fab.setOnClickListener {
      Log.d(TAG, "The user clicked favorite on the button")
      if (binding.get().saved) {
        // delete it
      } else {
        // save it
        if (binding.get().movie != null)
          viewModel.saveMovie(binding.get().movie!!)
      }
    }
  }

  companion object {

    private val MOVIE_ID = "movieId"
    private val TAG = MovieFragment::class.java.simpleName.toString()

    fun newInstance(movieId: Int): MovieFragment {
      val fragment = MovieFragment()
      val args = Bundle()
      args.putInt(MOVIE_ID, movieId)
      fragment.arguments = args
      return fragment
    }
  }
}
