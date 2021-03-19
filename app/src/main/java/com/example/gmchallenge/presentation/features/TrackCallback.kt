package com.example.gmchallenge.presentation.features

import androidx.recyclerview.widget.DiffUtil
import com.example.gmchallenge.data.models.Track

class TrackCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.trackId == newItem.trackId
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.trackId == newItem.trackId
    }
}