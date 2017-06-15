package com.example.vn008xw.carbeat.ui.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.vn008xw.carbeat.R
import com.example.vn008xw.carbeat.utils.DrawableUtil

/**
 * Created by vn008xw on 6/11/17.
 */
class ImageViewBindingAdapter {

    companion object {
        @BindingAdapter("imageUrl", "placeholder")
        fun loadImage(imageView: ImageView, url: String?, placeholder: Drawable) {
            Glide.with(imageView.context)
                    .load(url)
                    .placeholder(placeholder)
                    .error(DrawableUtil.getDrawable(imageView.context, R.drawable.ic_no_image))
                    .into(imageView)
        }
    }
}