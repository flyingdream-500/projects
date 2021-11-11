package com.example.voicerecorderapp.service

interface IMediaPlayer {
    fun startMediaPlayer()
    fun playMediaPlayer()
    fun pauseMediaPlayer()
    fun stopMediaPlayer()
    fun releaseMediaPlayer()
    fun completionMediaPlayerBehavior()
}