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
import java.io.File

class ListeningService: Service() {

    val mBinder = LocalBinder()
    val messenger = Messenger(IncomingHandler())

    private var mediaPlayer: MediaPlayer? = null

    private var defaultRecordItem: RecordItem? = null

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
            Log.d("SRC", "Service: handleMessage")
            when(msg.what) {
                MSG_EXAMPLE -> {
                    Toast.makeText(applicationContext, "Message Received", Toast.LENGTH_SHORT).show()
                    replyToFragment(msg.replyTo)
                }
                MSG_RECORD -> {
                    val recordItem = msg.data.getParcelable<RecordItem>("RECORD ITEM KEY")!!
                    mediaPlayer = MediaPlayer.create(this@ListeningService, Uri.fromFile(File(recordItem.path)))
                    mediaPlayer?.let {
                        if (it.isPlaying) {
                            it.pause()
                            startForeground(LISTENING_NOTIFICATION_ID, createPlayNotification(""))
                        } else {
                            mediaPlayer?.start()
                            startForeground(LISTENING_NOTIFICATION_ID, createPauseNotification(""))
                        }
                    }
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
        Log.d("SRC", "Service: onBind")
        Toast.makeText(applicationContext, "Bind", Toast.LENGTH_SHORT).show()
        return messenger.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            defaultRecordItem = intent.extras?.getParcelable<RecordItem>("RECORD ITEM KEY")
            Log.d("SRC", "Service: onStartCommand ${defaultRecordItem == null}")
            val name = defaultRecordItem?.name ?: "default"
            when(it.action) {
                ACTION_START -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPauseNotification(defaultRecordItem!!.name))
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(defaultRecordItem?.path))
                    mediaPlayer?.start()
                }
                ACTION_PLAY -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPauseNotification("defaultRecordItem!!.name"))
                    mediaPlayer?.start()
                }
                ACTION_PAUSE -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPlayNotification("defaultRecordItem!!.name"))
                    mediaPlayer?.pause()
                }
                ACTION_STOP -> {
                    startForeground(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(defaultRecordItem!!.path))

                    //stopForeground(true)
                    //stopSelf()
                    // getNotificationManager().cancel(LISTENING_NOTIFICATION_ID)
                }
                else -> startForeground(LISTENING_NOTIFICATION_ID, createPlayNotification(defaultRecordItem!!.name))
            }
        }
        return START_NOT_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("SRC", "Service: onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("SRC", "Service: onRebind")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SRC", "Service: onDestroy")
    }

    inner class LocalBinder: Binder() {
        fun getService() = this@ListeningService
    }

}