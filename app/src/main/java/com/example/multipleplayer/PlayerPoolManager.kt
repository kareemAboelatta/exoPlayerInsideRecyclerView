package com.example.multipleplayer

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

object PlayerPoolManager {
    private val playerPool = mutableListOf<ExoPlayer>()
    private var currentPlayer: ExoPlayer? = null


    fun startPlayer(player: ExoPlayer) {
        currentPlayer?.stop()
        currentPlayer = player
        currentPlayer?.play()
    }
    fun releaseAllPlayers() {
        currentPlayer?.stop()
        playerPool.forEach { it.release() }
        playerPool.clear()
    }

    fun getPlayer(context: Context): ExoPlayer {
        return if (playerPool.isNotEmpty()) {
            playerPool.removeAt(0)
        } else {
            ExoPlayer.Builder(context).build()
        }
    }

    fun releasePlayer(player: ExoPlayer) {
        player.stop()
        player.clearMediaItems()
        playerPool.add(player)
    }
}
