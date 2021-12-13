package com.example.voicerecorderapp.service

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.*
import android.text.format.DateUtils
import android.util.Log
import com.example.voicerecorderapp.service.background.IBackgroundTimer
import com.example.voicerecorderapp.service.background.BackgroundTimerImpl
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.COUNTER_KEY
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.MSG_BIND
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.PAUSE_RECORDING
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.START_RECORDING
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.STOP_RECORDING
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment.Companion.UPDATE_TIMER
import com.example.voicerecorderapp.service.interfaces.IMediaRecorder
import com.example.voicerecorderapp.service.interfaces.INotificationChanges
import com.example.voicerecorderapp.utils.notification.RecordingNotification.RECORDING_NOTIFICATION_ID
import com.example.voicerecorderapp.utils.notification.RecordingNotification.createPauseStopNotification
import com.example.voicerecorderapp.utils.notification.RecordingNotification.createPlayNotification
import com.example.voicerecorderapp.utils.notification.RecordingNotification.createPlayStopNotification
import com.example.voicerecorderapp.utils.notification.RecordingNotification.getNotificationManager
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RecordingService : Service(), IMediaRecorder, INotificationChanges {

    private var mediaRecorder: MediaRecorder? = MediaRecorder()
    private val parent = File(Environment.getExternalStorageDirectory(), "VoiceRecords/")

    private val messenger = Messenger(IncomingHandler())
    private var localMessenger: Messenger? = null
    private var CURRENT_ACTION = ""

    // Timer
    private lateinit var background: IBackgroundTimer


    companion object {

        const val ACTION_PLAY = "record_play"
        const val ACTION_PAUSE = "record_pause"
        const val ACTION_STOP = "record_stop"
        const val ACTION_START = "record_start"
        const val ACTION_EXIT = "record_exit"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(this)
        } else {
            mediaRecorder = MediaRecorder()
        }
        background = BackgroundTimerImpl(::timerUpdate)
    }


    inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_BIND -> {
                    Log.d("SRC", "Service: GET MESSAGE")
                    Log.d("SRR", "Service: CURRENT_ACTION: $CURRENT_ACTION")
                    localMessenger = msg.replyTo
                    when(CURRENT_ACTION){
                        ACTION_START -> {
                            playUiUpdate(localMessenger)
                            timerUpdate(background.counter)
                        }
                        ACTION_PLAY -> {
                            playUiUpdate(localMessenger)
                            timerUpdate(background.counter)
                        }
                        ACTION_PAUSE -> {
                            pauseUiUpdate(localMessenger)
                            timerUpdate(background.counter)
                        }
                        ACTION_STOP -> {
                            stopUiUpdate(localMessenger)
                            timerUpdate(background.counter)
                        }
                        ACTION_EXIT -> {
                            stopUiUpdate(localMessenger)
                            timerUpdate(background.counter)
                        }

                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private fun getTimeFormat(seconds: Long) =
        DateUtils.formatElapsedTime(seconds)


    private fun timerUpdate(counter: Long) {
        Log.d("TGG", "Service: UPDATE ${Thread.currentThread().name}")
        val time = getTimeFormat(counter)
        val message = Message.obtain(null, UPDATE_TIMER)
        val bundle = Bundle()
        bundle.putString(COUNTER_KEY, time)
        message.data = bundle
        localMessenger?.send(message)
    }

    private fun pauseUiUpdate(messenger: Messenger?) {
        val message = Message.obtain(null, PAUSE_RECORDING)
        messenger?.send(message)
        Log.d("SRC", "Service: SEND PAUSE MESSAGE ")
    }

    private fun stopUiUpdate(messenger: Messenger?) {
        val message = Message.obtain(null, STOP_RECORDING)
        messenger?.send(message)
        Log.d("SRC", "Service: SEND STOP MESSAGE ")
    }

    private fun playUiUpdate(messenger: Messenger?) {
        val message = Message.obtain(null, START_RECORDING)
        messenger?.send(message)
        Log.d("SRC", "Service: SEND PLAY MESSAGE")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            CURRENT_ACTION = it.action.toString()
            Log.d("CCC", "Service: ACTION: $CURRENT_ACTION")
            when (it.action) {
                ACTION_START -> {
                    startForeground(RECORDING_NOTIFICATION_ID, createPauseStopNotification())
                    startMediaRecorder()
                    background.start(true)
                }
                ACTION_PLAY -> {
                    playNotificationChange()
                    playMediaRecorder()
                    playUiUpdate(localMessenger)
                    background.start(true)
                }
                ACTION_PAUSE -> {
                    pauseNotificationChange()
                    pauseMediaRecorder()
                    pauseUiUpdate(localMessenger)
                    background.start(false)
                }
                ACTION_STOP -> {
                    stopNotificationChange()
                    stopMediaRecorder()
                    stopUiUpdate(localMessenger)
                    background.start(false)
                    background.counter = 0
                    timerUpdate(0)
                }
                ACTION_EXIT -> {
                    releaseMediaRecorder()
                    stopUiUpdate(localMessenger)
                    background.start(false)
                    background.counter = 0
                    timerUpdate(0)

                    stopForeground(true)
                    stopSelf()

                    background.cancel()
                }
                else -> startForeground(RECORDING_NOTIFICATION_ID, createPlayNotification())
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return messenger.binder
    }


    override fun startMediaRecorder() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(this)
        } else {
            mediaRecorder = MediaRecorder()
        }
        val fileName = getVoiceRecordName()
        val filePath = File(parent, fileName).absolutePath
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(filePath)
        mediaRecorder?.prepare()
        mediaRecorder?.start()
    }

    override fun playMediaRecorder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder?.resume()
        }
    }

    override fun pauseMediaRecorder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder?.pause()
        }
    }

    override fun stopMediaRecorder() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null;
    }

    override fun releaseMediaRecorder() {
        mediaRecorder?.release();
        mediaRecorder = null;
    }

    override fun playNotificationChange() {
        getNotificationManager().notify(RECORDING_NOTIFICATION_ID, createPauseStopNotification())
    }

    override fun stopNotificationChange() {
        getNotificationManager().notify(RECORDING_NOTIFICATION_ID, createPlayNotification())
    }

    override fun pauseNotificationChange() {
        getNotificationManager().notify(RECORDING_NOTIFICATION_ID, createPlayStopNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaRecorder()
        background.close()
        Log.d("TAGG", "Service: onDestroy")
    }


    fun getVoiceRecordName() = "REC_" +
        "${SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(Date())}.aac"

}
