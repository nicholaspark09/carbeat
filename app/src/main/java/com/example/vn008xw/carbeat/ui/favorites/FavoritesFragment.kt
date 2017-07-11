package com.example.vn008xw.carbeat.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.vn008xw.carbeat.AppComponent
import com.example.vn008xw.carbeat.MainActivity
import com.example.vn008xw.carbeat.base.BaseView
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.data.vo.Status
import com.example.vn008xw.carbeat.databinding.FragmentFavoritesBinding
import com.example.vn008xw.carbeat.ui.NavigationController
import com.example.vn008xw.carbeat.utils.AutoClearedValue
import javax.inject.Inject

class FavoritesFragment : BaseView() {

  @Inject lateinit var factory: ViewModelProvider.Factory
  @Inject lateinit var navigationController: NavigationController
  private lateinit var viewModel: FavoritesViewModel
  private lateinit var binding: AutoClearedValue<FragmentFavoritesBinding>
  private lateinit var moviesAdapter: AutoClearedValue<FavoritesListAdapter>

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
      FragmentFavoritesBinding.inflate(inflater!!, container, false).apply {
        binding = AutoClearedValue(this@FavoritesFragment, this)
        setHasOptionsMenu(true)
      }.root

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel::class.java)

    val adapter = FavoritesListAdapter(object: FavoritesListAdapter.FavoriteCallback {

      override fun onClick(movie: Movie) {
         navigationController.navigateToMovie(activity as MainActivity, movie.id, null)
      }
    })
    moviesAdapter = AutoClearedValue(this, adapter)
    binding.get().adapter = adapter
    viewModel.movies.observe(this@FavoritesFragment, Observer {
      when (it?.status) {
        Status.ERROR -> Toast.makeText(context, "Couldn't find any", Toast.LENGTH_LONG)
        Status.SUCCESS -> {
          moviesAdapter.get().replace(it?.data)
        }
      }
      setLoading(it?.status)
    })

    viewModel.find.postValue(true)
  }

  fun setLoading(status: Status?) {
    val loading = status == Status.LOADING
    binding.get().loading = loading
  }

  override fun inject(appComponent: AppComponent) {
    appComponent.inject(this)
  }

  companion object {
    private val TAG = FavoritesFragment::class.java.simpleName.toString()

    fun newInstance() = FavoritesFragment()
  }
}