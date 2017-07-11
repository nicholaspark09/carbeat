package com.example.vn008xw.carbeat.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.databinding.ListItemMovieBinding
import com.example.vn008xw.carbeat.utils.ImageUtil
import com.example.vn008xw.carbeat.view.DataBoundAdapter

class FavoritesListAdapter(private val favoriteCallback: FavoriteCallback) : DataBoundAdapter<FavoriteMovie, ListItemMovieBinding>() {

  override fun bind(binding: ListItemMovieBinding, item: FavoriteMovie) {
    binding.movie = item.movie
    binding.imageName = ImageUtil.getLittleImage(item.movie.posterPath) ?: ""
  }

  override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
    return oldItem.id == newItem.id && oldItem.equals(newItem)
  }

  override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
    return oldItem.movie.title.equals(newItem.movie.title) && oldItem.equals(newItem)
  }

  override fun createBinding(parent: ViewGroup): ListItemMovieBinding {
    val binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context))

    binding.root.setOnClickListener {
      if (binding.movie != null)
        favoriteCallback.onClick(binding.movie!!)
    }
    return binding
  }

  interface FavoriteCallback {
    fun onClick(movie: Movie)
  }
}