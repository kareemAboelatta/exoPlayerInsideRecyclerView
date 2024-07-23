package com.example.multipleplayer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.example.multipleplayer.databinding.ItemVideoBinding

class VideoAdapter(private val videoUris: List<String>, private val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoUris[position], context)
    }

    override fun getItemCount(): Int = videoUris.size

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.play()
    }
}


class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
    private var player: ExoPlayer? = null

    fun bind(videoUri: String, context: Context) {
        player = PlayerPoolManager.getPlayer(context)
        binding.videoView.player = player

        player?.let {player->
            val mediaItem = MediaItem.fromUri(videoUri)
            player.setMediaItem(mediaItem)
            player.prepare()
            PlayerPoolManager.startPlayer(player)
        }

    }

    fun play() {
        player?.let { PlayerPoolManager.startPlayer(it) }
    }

    fun recycle() {
        player?.let {
            PlayerPoolManager.releasePlayer(it)
        }
    }
}
