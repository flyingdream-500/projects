package com.example.voicerecorderapp.service.interfaces

interface IMediaRecorder {
    fun startMediaRecorder()
    fun playMediaRecorder()
    fun pauseMediaRecorder()
    fun stopMediaRecorder()
    fun releaseMediaRecorder()
}