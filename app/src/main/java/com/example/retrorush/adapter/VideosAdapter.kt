package com.example.retrorush.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.retrorush.R
import com.example.retrorush.databinding.ListItemVideoBinding
import com.example.retrorush.models.Results
import com.example.retrorush.repository.TheMovieDatabaseAPI
import com.squareup.picasso.Picasso

class VideosAdapter(
    private val itemClickListener: OnVideoItemClickListener ,
    private val context : Context
) :
    ListAdapter<Results, VideosAdapter.ViewHolder>(VideoDiffCallback()) {

    inner class ViewHolder(private val binding: ListItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(videos: Results, videoItemClickListener: OnVideoItemClickListener) {
            with(binding) {
                Picasso.with(context).load(TheMovieDatabaseAPI.getYoutubeImageUrl(videos.key!!)).fit().centerCrop()
                    .placeholder(R.drawable.sample)
                    .error(R.drawable.sample)
                    .into(image);

//                image.load(TheMovieDatabaseAPI.getYoutubeImageUrl(videos.key!!)) {
//                    crossfade(true)
//                    placeholder(R.drawable.sample)
//                    transformations(RoundedCornersTransformation(10f))
//                }

                playImageButton.setOnClickListener {
                    videoItemClickListener.onClick(videos)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: VideosAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)

    }

}

class VideoDiffCallback : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem == newItem
    }

}

interface OnVideoItemClickListener {
    fun onClick(videos: Results)
}