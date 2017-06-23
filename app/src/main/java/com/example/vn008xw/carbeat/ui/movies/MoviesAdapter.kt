package com.example.vn008xw.carbeat.ui.movies

import android.annotation.SuppressLint
import android.support.annotation.MainThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vn008xw.carbeat.AppExecutors
import com.example.vn008xw.carbeat.data.vo.Movie
import com.example.vn008xw.carbeat.databinding.ListItemMovieBinding
import view.BindingViewHolder
import android.provider.SyncStateContract.Helpers.update
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.vn008xw.carbeat.utils.getSmallImageUrl


/**
 * Created by vn008xw on 6/16/17.
 */

typealias MovieRowViewHolder = BindingViewHolder<ListItemMovieBinding>

class MoviesAdapter constructor(private val appExecutors: AppExecutors)
    : RecyclerView.Adapter <MovieRowViewHolder>() {

    var items: List<Movie> = arrayListOf()
    var dataVersion: Int = 0


    @SuppressLint("StaticFieldLeak")
    @MainThread
    open fun replace(updates: List<Movie>?) {
        dataVersion.inc()
        if (items == null || items.size == 0) {
            items = updates ?: return
            notifyDataSetChanged()
        } else if (updates == null) {
            val oldSize = items.size
            items = arrayListOf()
            notifyItemRangeRemoved(0, oldSize)
        } else {
            val startVersion = dataVersion
            val oldItems = items.toList()

            Log.d("Tag", "Trying to find the diff");
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieRowViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BindingViewHolder<ListItemMovieBinding>, position: Int) {
        holder.viewDataBinding.apply {
            title = items[position].title
            imageName = items[position].posterPath.getSmallImageUrl()
            executePendingBindings()
        }
    }
}