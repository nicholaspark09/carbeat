package com.example.vn008xw.carbeat.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vn008xw.carbeat.data.vo.Poster
import com.example.vn008xw.carbeat.databinding.ListItemMovieImageBinding
import com.example.vn008xw.carbeat.utils.getSmallImageUrl
import com.example.vn008xw.carbeat.view.DataBoundAdapter

class MovieImageListAdapter(private val imageCallback: MovieImageCallback?) : DataBoundAdapter<Poster, ListItemMovieImageBinding>() {

  override fun createBinding(parent: ViewGroup) =
      ListItemMovieImageBinding.inflate(LayoutInflater.from(parent.context)).apply {
        root.setOnClickListener({ _ ->
          if (poster != null) imageCallback?.onClick(poster!!)
        })
      }

  override fun bind(binding: ListItemMovieImageBinding, item: Poster) = binding.run {
    poster = item
    loading = false
    if (item.filePath != null) {
      imageUrl = item.filePath.getSmallImageUrl()
    }
  }


  override fun areItemsTheSame(oldItem: Poster?, newItem: Poster?): Boolean {
    return oldItem != null && oldItem?.filePath == newItem?.filePath && oldItem.equals(newItem)
  }

  override fun areContentsTheSame(oldItem: Poster?, newItem: Poster?): Boolean {
    return oldItem != null && oldItem?.filePath == newItem?.filePath && oldItem.equals(newItem)
  }

  interface MovieImageCallback {
    fun onClick(poster: Poster)
  }
}