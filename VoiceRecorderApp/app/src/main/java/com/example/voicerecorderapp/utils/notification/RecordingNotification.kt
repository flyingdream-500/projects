package com.example.voicerecorderapp.utils.notification

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
import com.example.voicerecorderapp.presentation.MainActivity
import com.example.voicerecorderapp.presentation.MainActivity.Companion.RECORDING_FRAGMENT_NUMBER
import com.example.voicerecorderapp.service.RecordingService


object RecordingNotification {
    const val RECORDING_CHANNEL_ID = "channel recording"
    const val RECORDING_NOTIFICATION_ID = 123

    fun ContextWrapper.createPlayNotification(): Notification {
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.recording_notification_play)
        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.recording_collapsed_notification_play)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.startImpl(this)
        list.exitImpl(this)


        getNotificationManager().also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createListeningChannel(this)
            }
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, RECORDING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_play)
                .setCustomContentView(collapsedRemoteViews)
                .setCustomBigContentView(expandedRemoteViews)

        return builder.build()
    }

    fun ContextWrapper.createPlayStopNotification(): Notification {
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.recording_notification_play_stop)
        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.recording_collapsed_notification_play_stop)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.playImpl(this)
        list.stopImpl(this)
        list.exitImpl(this)

        getNotificationManager().also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createListeningChannel(this)
            }
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, RECORDING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_play)
                .setCustomContentView(collapsedRemoteViews)
                .setCustomBigContentView(expandedRemoteViews)

        return builder.build()
    }

    fun ContextWrapper.createPauseStopNotification(): Notification {
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.recording_notification_pause_stop)
        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.recording_collapsed_notification_pause_stop)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.pauseImpl(this)
        list.stopImpl(this)
        list.exitImpl(this)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, RECORDING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_pause)
                .setCustomContentView(collapsedRemoteViews)
                .setCustomBigContentView(expandedRemoteViews)

        return builder.build()

    }


    fun ContextWrapper.getNotificationManager() =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    @RequiresApi(Build.VERSION_CODES.O)
    private fun NotificationManager.createListeningChannel(
        contextWrapper: ContextWrapper,
    ) {
        contextWrapper.run {
            val name = getString(R.string.recording_notification_name)
            val description = getString(R.string.recording_notification_description)

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
    }

    private fun List<RemoteViews>.bodyImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val intent = Intent(this, MainActivity::class.java)
            intent.action = RECORDING_FRAGMENT_NUMBER
            val pendingOpen =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.root,
                    pendingOpen
                )
            }
        }
    }

    private fun List<RemoteViews>.pauseImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val pause = Intent(this, RecordingService::class.java).also {
                it.action = RecordingService.ACTION_PAUSE
            }
            val pendingPause =
                PendingIntent.getService(this, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.record_pause,
                    pendingPause
                )
            }
        }
    }

    private fun List<RemoteViews>.stopImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val stop = Intent(this, RecordingService::class.java).also {
                it.action = RecordingService.ACTION_STOP
            }
            val pendingStop =
                PendingIntent.getService(this, 0, stop, PendingIntent.FLAG_UPDATE_CURRENT)
            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.record_stop,
                    pendingStop
                )
            }
        }
    }

    private fun List<RemoteViews>.playImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val play = Intent(this, RecordingService::class.java).also {
                it.action = RecordingService.ACTION_PLAY
            }
            val pendingPlay =
                PendingIntent.getService(this, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.record_play,
                    pendingPlay
                )
            }
        }
    }

    private fun List<RemoteViews>.startImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val play = Intent(this, RecordingService::class.java).also {
                it.action = RecordingService.ACTION_START
            }
            val pendingPlay =
                PendingIntent.getService(this, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.record_play,
                    pendingPlay
                )
            }
        }
    }

    private fun List<RemoteViews>.exitImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val play = Intent(this, RecordingService::class.java).also {
                it.action = RecordingService.ACTION_EXIT
            }
            val pendingExit =
                PendingIntent.getService(this, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
            forEach { remoteViews ->
                remoteViews.setOnClickPendingIntent(
                    R.id.exit,
                    pendingExit
                )
            }
        }
    }


}