package com.example.vn008xw.carbeat.ui.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vn008xw.carbeat.data.vo.Cast
import com.example.vn008xw.carbeat.databinding.ListItemCastBinding
import com.example.vn008xw.carbeat.utils.ImageUtil
import com.example.vn008xw.carbeat.view.DataBoundAdapter

class CastListAdapter : DataBoundAdapter<Cast, ListItemCastBinding>() {

  override fun createBinding(parent: ViewGroup) =
      ListItemCastBinding.inflate(LayoutInflater.from(parent.context))

  override fun bind(binding: ListItemCastBinding, item: Cast) {
    binding.apply {
      cast = item
      if (ImageUtil.getLittleImage(item.profilePath) != null)
        imageName = ImageUtil.getLittleImage(item.profilePath)
    }
  }

  override fun areItemsTheSame(oldItem: Cast, newItem: Cast) =
      oldItem.castId == newItem.castId && oldItem == newItem

  override fun areContentsTheSame(oldItem: Cast, newItem: Cast) =
      oldItem.name == newItem.name && oldItem == newItem
}