package com.example.voicerecorderapp.utils.allrecords

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.voicerecorderapp.R
import com.example.voicerecorderapp.service.ListeningService
import com.example.voicerecorderapp.service.RecordingService

object RecordingNotification {
    const val RECORDING_CHANNEL_ID = "channel recording"
    const val RECORDING_NOTIFICATION_ID = 123

    @RequiresApi(Build.VERSION_CODES.O)
    private fun NotificationManager.createListeningChannel(name: String, description: String) {
        val channel =
            NotificationChannel(
                RECORDING_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_HIGH
            ).also {
                it.description = description
            }
        createNotificationChannel(channel)
    }

    fun ContextWrapper.createPlayNotification(trackName: String): Notification {
        val remoteViews =
            RemoteViews(packageName, R.layout.recording_notification_play_layout)

        remoteViews.setTextViewText(R.id.record_name, trackName)

        //Play click
        val play = Intent(this, RecordingService::class.java).also {
            it.action = RecordingService.ACTION_PLAY
        }
        val pendingPlay = PendingIntent.getService(this, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.record_play, pendingPlay)

        //Exit click
        val exit = Intent(this, RecordingService::class.java).also {
            it.action = RecordingService.ACTION_EXIT
        }
        val pendingExit = PendingIntent.getService(this, 0, exit, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.exit, pendingExit)


        val notificationManager = getNotificationManager()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.recording_notification_name)
            val description = getString(R.string.recording_notification_description)
            notificationManager.createListeningChannel(name, description)
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, RECORDING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_play)
                .setCustomContentView(remoteViews)

        return builder.build()

    }

    @SuppressLint("RemoteViewLayout")
    fun ContextWrapper.createPauseNotification(trackName: String): Notification {
        val remoteViews =
            RemoteViews(packageName, R.layout.recording_notification_pause_layout)

        remoteViews.setTextViewText(R.id.record_name, trackName)

        //Pause click
        val pause = Intent(this, RecordingService::class.java).also {
            it.action = RecordingService.ACTION_PAUSE
        }
        val pendingPause = PendingIntent.getService(this, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.record_pause, pendingPause)

        //Stop click
        val stop = Intent(this, RecordingService::class.java).also {
            it.action = RecordingService.ACTION_STOP
        }
        val pendingStop = PendingIntent.getService(this, 0, stop, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.record_stop, pendingStop)

        //Exit click
        val exit = Intent(this, RecordingService::class.java).also {
            it.action = RecordingService.ACTION_EXIT
        }
        val pendingExit = PendingIntent.getService(this, 0, exit, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.exit, pendingExit)


        val notificationManager = getNotificationManager()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.recording_notification_name)
            val description = getString(R.string.recording_notification_description)
            notificationManager.createListeningChannel(name, description)
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, RECORDING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_pause)
                .setCustomContentView(remoteViews)

        return builder.build()

    }



    fun ContextWrapper.startNotification(trackName: String) {
        val notification = createPlayNotification(trackName).also {
            it.flags = it.flags or Notification.FLAG_ONGOING_EVENT
        }
        getNotificationManager().notify(RECORDING_NOTIFICATION_ID, notification)
    }

    fun ContextWrapper.getNotificationManager() =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}