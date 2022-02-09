package com.example.retrorush.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.retrorush.R
import com.example.retrorush.databinding.ListItemMovieGridBinding
import com.example.retrorush.models.Result

class MoviesAdapter : ListAdapter<Result, MoviesAdapter.ViewHolder>(MovieDiffCallback()) {
    inner class ViewHolder(private val binding: ListItemMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            with(binding) {
                image.load(movie.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.sample)
                    transformations(RoundedCornersTransformation(10f))
                }
                titleText.text = movie.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMovieGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

}