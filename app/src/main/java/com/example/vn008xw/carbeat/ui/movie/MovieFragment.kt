package com.example.vn008xw.carbeat.ui.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.base.BaseView
import com.example.vn008xw.carbeat.data.vo.Status
import com.example.vn008xw.carbeat.databinding.FragmentMovieBinding
import com.example.vn008xw.carbeat.utils.AutoClearedValue
import com.example.vn008xw.carbeat.utils.ImageUtil
import com.example.vn008xw.carbeat.utils.getLargeImageUrl
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
                binding = AutoClearedValue(this@MovieFragment, this)
            }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        viewModel.getMovie().observe(this@MovieFragment, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    Log.d("MovieFragment", "You got a movie back: " + it?.data?.title)
                    binding.get()?.movie = it.data
                    binding.get()?.imageUrl = it.data?.posterPath?.getLargeImageUrl()
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.movieId.postValue(movieId)
    }

    companion object {

        private val MOVIE_ID = "movieId"

        fun newInstance(movieId: Int): MovieFragment {
            val fragment = MovieFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
