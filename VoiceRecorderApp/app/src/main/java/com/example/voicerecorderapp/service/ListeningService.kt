package com.example.voicerecorderapp.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.util.Log
import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment
import com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment.Companion.MSG_BIND
import com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment.Companion.RECORD_PATH_KEY
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment
import com.example.voicerecorderapp.service.interfaces.IMediaPlayer
import com.example.voicerecorderapp.service.interfaces.INotificationChanges
import com.example.voicerecorderapp.utils.notification.ListeningNotification.LISTENING_NOTIFICATION_ID
import com.example.voicerecorderapp.utils.notification.ListeningNotification.createPauseStopNotification
import com.example.voicerecorderapp.utils.notification.ListeningNotification.createPlayNotification
import com.example.voicerecorderapp.utils.notification.ListeningNotification.createPlayStopNotification
import com.example.voicerecorderapp.utils.notification.ListeningNotification.getNotificationManager

class ListeningService: Service(), IMediaPlayer, INotificationChanges {


    private val messenger = Messenger(IncomingHandler())
    private var localMessenger: Messenger? = null
    private var CURRENT_ACTION = ""

    private var mediaPlayer: MediaPlayer? = null
    private var defaultRecordItem: RecordItem? = null


    companion object {
        const val ACTION_PLAY = "audio_play"
        const val ACTION_PAUSE = "audio_pause"
        const val ACTION_STOP= "audio_stop"

        const val ACTION_START = "audio_start"
        const val ACTION_EXIT= "audio_exit"
    }

    inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                MSG_BIND -> {
                    Log.d("SRC", "Service: GET MESSAGE")
                    Log.d("SRR", "Service: CURRENT_ACTION: $CURRENT_ACTION")
                    localMessenger = msg.replyTo
                    when(CURRENT_ACTION){
                        ACTION_START -> {
                            playListeningAnimation(localMessenger, defaultRecordItem?.path)
                        }
                        ACTION_PLAY -> {
                            playListeningAnimation(localMessenger, defaultRecordItem?.path)
                        }
                        ACTION_PAUSE -> {
                            stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                        }
                        ACTION_STOP -> {
                            stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                        }
                        ACTION_EXIT -> {
                            stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                        }

                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private fun stopListeningAnimation(messenger: Messenger?, path: String?) {
        val message = Message.obtain(null, AllRecordsFragment.STOP_LISTENING)
        val bundle = Bundle()
        bundle.putString(RECORD_PATH_KEY, path)
        message.data = bundle
        messenger?.send(message)
        Log.d("SRC", "Service: SEND STOP MESSAGE $path")
    }

    private fun playListeningAnimation(messenger: Messenger?, path: String?) {
        val message = Message.obtain(null, AllRecordsFragment.START_LISTENING)
        val bundle = Bundle()
        bundle.putString(RECORD_PATH_KEY, path)
        message.data = bundle
        messenger?.send(message)
        Log.d("SRC", "Service: SEND PLAY MESSAGE $path")
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
            stopListeningAnimation(localMessenger, defaultRecordItem?.path)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            CURRENT_ACTION = it.action.toString()
            val recordItem = intent.extras?.getParcelable<RecordItem>(AllRecordsFragment.RECORD_ITEM_KEY)
            if (recordItem != null) {
                this.defaultRecordItem = recordItem
            }
            when(it.action) {
                ACTION_START -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPauseStopNotification(defaultRecordItem!!.name))
                    releaseMediaPlayer()
                    startMediaPlayer()
                    completionMediaPlayerBehavior()
                }
                ACTION_PLAY -> {
                    playNotificationChange()
                    playMediaPlayer()
                    playListeningAnimation(localMessenger, defaultRecordItem?.path)
                }
                ACTION_PAUSE -> {
                    pauseNotificationChange()
                    pauseMediaPlayer()
                    stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                }
                ACTION_STOP -> {
                    stopNotificationChange()
                    stopMediaPlayer()
                    stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                }
                ACTION_EXIT -> {
                    releaseMediaPlayer()
                    stopListeningAnimation(localMessenger, defaultRecordItem?.path)
                    stopForeground(true)
                    stopSelf()
                }
                else -> startForeground(LISTENING_NOTIFICATION_ID, createPlayStopNotification(defaultRecordItem!!.name))
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
        Log.d("SRC", "Service: onDestroy")
    }

    inner class LocalBinder: Binder() {
        fun getService() = this@ListeningService
    }

    override fun playNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPauseStopNotification(defaultRecordItem!!.name))
    }

    override fun stopNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
    }

    override fun pauseNotificationChange() {
        getNotificationManager().notify(LISTENING_NOTIFICATION_ID, createPlayStopNotification(defaultRecordItem!!.name))
    }

}