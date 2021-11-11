package com.example.voicerecorderapp.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.util.Log
import android.widget.Toast
import com.example.voicerecorderapp.data.RecordItem
import com.example.voicerecorderapp.presentation.AllRecordsFragment
import com.example.voicerecorderapp.utils.allrecords.ListeningNotification.LISTENING_NOTIFICATION_ID
import com.example.voicerecorderapp.utils.allrecords.ListeningNotification.createPauseNotification
import com.example.voicerecorderapp.utils.allrecords.ListeningNotification.createPlayNotification
import com.example.voicerecorderapp.utils.allrecords.ListeningNotification.getNotificationManager
import java.io.File

class ListeningService: Service(), IMediaPlayer, INotificationChanges {

    val mBinder = LocalBinder()
    val messenger = Messenger(IncomingHandler())

    private var mediaPlayer: MediaPlayer? = null

    private var defaultRecordItem: RecordItem? = null

    var resetPlaying: () -> Unit = {}

    companion object {
        val MSG_EXAMPLE: Int = 1
        val MSG_RECORD: Int = 10


        val ACTION_PLAY = "audio_play"
        val ACTION_PAUSE = "audio_pause"
        val ACTION_STOP= "audio_stop"

        val ACTION_START = "audio_start"
        val ACTION_EXIT= "audio_exit"
    }

    inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {

            when(msg.what) {
                MSG_EXAMPLE -> {
                    Log.d("SRC", "Service: GET MESSAGE")
                    resetPlaying = {replyToFragment(msg.replyTo) }
                    //replyToFragment(msg.replyTo)
                }
                MSG_RECORD -> {

                }
                else -> super.handleMessage(msg)
            }

        }
    }
    fun replyToFragment(messenger: Messenger) {
        Log.d("SRC", "Service: replyToFragment")
        val message = Message.obtain(null, AllRecordsFragment.MSG_RESPONSE)
        messenger.send(message)
    }


    override fun onCreate() {
        super.onCreate()
        Log.d("SRC", "Service: onCreate")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return messenger.binder
    }




    override fun startMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, Uri.parse(defaultRecordItem!!.path))
        mediaPlayer?.start()
    }

    override fun playMediaPlayer() {
        mediaPlayer?.start()
    }

    override fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, Uri.parse(defaultRecordItem!!.path))
    }

    override fun pauseMediaPlayer() {
        mediaPlayer?.pause()
        //resetPlaying.invoke()
    }

    override fun releaseMediaPlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun completionMediaPlayerBehavior() {
        mediaPlayer?.setOnCompletionListener {
            getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
            stopMediaPlayer()
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val recordItem = intent.extras?.getParcelable<RecordItem>("RECORD ITEM KEY")
            if (recordItem != null) {
                this.defaultRecordItem = recordItem
            }
            when(it.action) {
                ACTION_START -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPauseNotification(defaultRecordItem!!.name))
                    releaseMediaPlayer()
                    startMediaPlayer()
                    completionMediaPlayerBehavior()
                }
                ACTION_PLAY -> {
                    playNotificationChange()
                    playMediaPlayer()
                }
                ACTION_PAUSE -> {
                    pauseNotificationChange()
                    pauseMediaPlayer()
                }
                ACTION_STOP -> {
                    stopNotificationChange()
                    stopMediaPlayer()
                }
                ACTION_EXIT -> {
                    releaseMediaPlayer()
                    stopForeground(true)
                    stopSelf()
                }
                else -> startForeground(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SRC", "Service: onDestroy")
    }

    inner class LocalBinder: Binder() {
        fun getService() = this@ListeningService
    }

    override fun playNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPauseNotification(defaultRecordItem!!.name))
    }

    override fun stopNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
    }

    override fun pauseNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
    }

}