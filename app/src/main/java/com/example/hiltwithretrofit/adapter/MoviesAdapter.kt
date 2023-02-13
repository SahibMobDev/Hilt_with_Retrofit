package com.example.hiltwithretrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.hiltwithretrofit.R
import com.example.hiltwithretrofit.databinding.ItemMoviesBinding
import com.example.hiltwithretrofit.response.MoviesListResponse
import com.example.hiltwithretrofit.utils.Constants.POSTER_BASEURL
import javax.inject.Inject

class MoviesAdapter @Inject constructor() : PagingDataAdapter<MoviesListResponse.Result, MoviesAdapter.ViewHolder>(diffUtilCallback) {

    lateinit var binding: ItemMoviesBinding
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        binding = ItemMoviesBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MoviesListResponse.Result) {
            binding.apply {
                tvMovieName.text = item.originalTitle
                tvLang.text = item.originalLanguage
                tvRate.text = item.voteAverage.toString()
                tvMovieDateRelease.text = item.releaseDate
                val moviePoster = POSTER_BASEURL + item.posterPath
                imgMovie.load(moviePoster) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((MoviesListResponse.Result) -> Unit)? = null
    fun setOnItemClickListener(listener: (MoviesListResponse.Result) -> Unit) {
        onItemClickListener = listener
    }

    companion object {

        private val diffUtilCallback = object : DiffUtil.ItemCallback<MoviesListResponse.Result>() {
            override fun areItemsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}