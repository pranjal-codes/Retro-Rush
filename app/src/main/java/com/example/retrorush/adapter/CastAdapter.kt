package com.example.retrorush.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.retrorush.R
import com.example.retrorush.databinding.ListItemCastBinding
import com.example.retrorush.models.Cast
import com.example.retrorush.repository.TheMovieDatabaseAPI
import com.squareup.picasso.Picasso

class CastAdapter(private val context : Context) :
    ListAdapter<Cast, CastAdapter.ViewHolder>(CastDiffCallback()) {

    inner class ViewHolder(private val binding: ListItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            with(binding) {

                if (cast.profilePath != null) {
                    Picasso.with(context).load(TheMovieDatabaseAPI.getProfileUrl(cast.profilePath!!)).fit().centerCrop()
                        .placeholder(R.drawable.sample)
                        .error(R.drawable.sample)
                        .into(image);

//                    image.load(TheMovieDatabaseAPI.getProfileUrl(cast.profilePath!!)) {
//                        crossfade(true)
//                        placeholder(R.drawable.sample)
//                        transformations(RoundedCornersTransformation(10f))
//                    }
                } else {
                    image.load(R.drawable.sample) {
                        crossfade(true)
                        placeholder(R.drawable.sample)
                        transformations(RoundedCornersTransformation(10f))
                    }
                }
                titleText.text = cast.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

}

class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }

}