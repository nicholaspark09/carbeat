package com.example.vn008xw.carbeat.ui.movies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.databinding.ListItemMovieBinding
import view.BindingViewHolder

/**
 * Created by vn008xw on 6/16/17.
 */

typealias MovieRowViewHolder = BindingViewHolder<ListItemMovieBinding>

class MoviesAdapter internal constructor(private val items: List<Movie>) : RecyclerView.Adapter <MovieRowViewHolder>() {

    open fun addMovies(movies: List<Movie>) {
        items.plus(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieRowViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BindingViewHolder<ListItemMovieBinding>, position: Int) {
        holder.viewDataBinding.apply {
            title = items[position].title
            image = items[position].posterPath
            executePendingBindings()
        }
    }
}