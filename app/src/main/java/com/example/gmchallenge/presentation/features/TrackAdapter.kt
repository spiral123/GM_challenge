package com.example.gmchallenge.presentation.features

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gmchallenge.app.utils.toDisplayDate
import com.example.gmchallenge.data.models.Track
import com.example.gmchallenge.databinding.TrackRowBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TrackAdapter : ListAdapter<Track, TrackAdapter.ViewHolder>(TrackCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TrackRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = currentList[position]
        setDataOnView(holder, track)
    }

    inner class ViewHolder(val binding: TrackRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val artistName: TextView = binding.artistName
        val primaryGenreName: TextView = binding.primaryGenreName
        val trackName: TextView = binding.trackName
        val releaseDate: TextView = binding.releaseDate
        val price: TextView = binding.price
    }

    @SuppressLint("SetTextI18n")
    fun setDataOnView(holder: ViewHolder, track: Track) {
        holder.artistName.text = track.artistName
        holder.primaryGenreName.text = track.primaryGenreName
        holder.trackName.text = track.trackName
        holder.releaseDate.text = "release date: ${track.releaseDate.toDisplayDate}"
        holder.price.text = if (track.trackPrice.toString() != "null") {
            "price: ${track.trackPrice.toString()}"
        } else {
            "price unknown"
        }
    }
}
