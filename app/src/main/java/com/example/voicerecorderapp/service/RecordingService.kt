package com.example.voicerecorderapp.service

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RecordingService: Service(), IMediaRecorder, INotificationChanges {

    private var mediaRecorder: MediaRecorder? = MediaRecorder()

    companion object {
        val ACTION_PLAY = "record_play"
        val ACTION_PAUSE = "record_pause"
        val ACTION_STOP= "record_stop"

        val ACTION_START = "record_start"
        val ACTION_EXIT= "record_exit"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun startMediaRecorder() {

    }

    override fun playMediaRecorder() {
        val parent = File(Environment.getExternalStorageDirectory(), "VoiceRecords/")
        //val fileName =
        val fileName = "${SimpleDateFormat.getTimeInstance().format(Date())}.txt"
        TODO("Not yet implemented")
    }

    override fun pauseMediaRecorder() {
        TODO("Not yet implemented")
    }

    override fun stopMediaRecorder() {
        TODO("Not yet implemented")
    }

    override fun releaseMediaRecorder() {
        TODO("Not yet implemented")
    }

    override fun completionMediaRecorderBehavior() {
        TODO("Not yet implemented")
    }

    override fun playNotificationChange() {
        TODO("Not yet implemented")
    }

    override fun stopNotificationChange() {
        TODO("Not yet implemented")
    }

    override fun pauseNotificationChange() {
        TODO("Not yet implemented")
    }


}

fun main() {
    val fileName = "${SimpleDateFormat.getDateTimeInstance().format(Date())}.txt"
    print(fileName)
}