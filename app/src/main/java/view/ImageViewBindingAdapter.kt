package view

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {

    @BindingAdapter("imageResource")
    fun setImageResource(imageView: ImageView, @DrawableRes resource: Int) {
        imageView.setImageResource(resource)
    }

    @BindingAdapter("imageUrl", "placeholder")
    fun loadImage(imageView: ImageView, url: String?, placeholder: Drawable) {
        Glide.with(imageView.context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView)
    }

    @BindingAdapter("imageUrl", "placeholder", "error")
    fun loadImage(imageView: ImageView, url: String?, placeholder: Drawable?, error: Drawable?) {
        Glide.with(imageView.context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(imageView)
    }
}