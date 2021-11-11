package com.example.voicerecorderapp.service

interface IMediaRecorder {
    fun startMediaRecorder()
    fun playMediaRecorder()
    fun pauseMediaRecorder()
    fun stopMediaRecorder()
    fun releaseMediaRecorder()
    fun completionMediaRecorderBehavior()
}