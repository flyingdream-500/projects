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
import com.example.voicerecorderapp.presentation.MainActivity.Companion.LISTENING_FRAGMENT_NUMBER
import com.example.voicerecorderapp.service.ListeningService

object ListeningNotification {

    const val LISTENING_CHANNEL_ID = "channel listening"
    const val LISTENING_NOTIFICATION_ID = 122


    fun ContextWrapper.createPlayNotification(trackName: String): Notification {
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.listening_notification_play)
        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.listening_collapsed_notification_play)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.nameImpl(this, trackName)
        list.startImpl(this)
        list.exitImpl(this)


        getNotificationManager().also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createListeningChannel(this)
            }
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, LISTENING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_play)
                .setCustomContentView(collapsedRemoteViews)
                .setCustomBigContentView(expandedRemoteViews)

        return builder.build()
    }

    fun ContextWrapper.createPlayStopNotification(trackName: String): Notification {
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.listening_notification_play_stop)

        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.listening_collapsed_notification_play_stop)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.nameImpl(this, trackName)
        list.playImpl(this)
        list.stopImpl(this)
        list.exitImpl(this)

        getNotificationManager().also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createListeningChannel(this)
            }
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, LISTENING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_play)
                .setCustomContentView(collapsedRemoteViews)
                .setCustomBigContentView(expandedRemoteViews)

        return builder.build()
    }

    fun ContextWrapper.createPauseStopNotification(trackName: String): Notification {
        val collapsedRemoteViews =
            RemoteViews(packageName, R.layout.listening_collapsed_notification_pause_stop)
        val expandedRemoteViews =
            RemoteViews(packageName, R.layout.listening_notification_pause_stop)

        val list = listOf(collapsedRemoteViews, expandedRemoteViews)
        list.bodyImpl(this)
        list.nameImpl(this, trackName)
        list.pauseImpl(this)
        list.stopImpl(this)
        list.exitImpl(this)

        getNotificationManager().also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createListeningChannel(this)
            }
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, LISTENING_CHANNEL_ID)
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
            val name = getString(R.string.listening_notification_name)
            val description = getString(R.string.listening_notification_description)

            val channel =
                NotificationChannel(
                    LISTENING_CHANNEL_ID,
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
            intent.action = LISTENING_FRAGMENT_NUMBER
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

    private fun List<RemoteViews>.nameImpl(
        contextWrapper: ContextWrapper,
        trackName: String
    ) {
        contextWrapper.run {
            forEach { remoteViews ->
                remoteViews.setTextViewText(R.id.record_name, trackName)
            }
        }
    }

    private fun List<RemoteViews>.pauseImpl(
        contextWrapper: ContextWrapper
    ) {
        contextWrapper.run {
            val pause = Intent(this, ListeningService::class.java).also {
                it.action = ListeningService.ACTION_PAUSE
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
            val stop = Intent(this, ListeningService::class.java).also {
                it.action = ListeningService.ACTION_STOP
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
            val play = Intent(this, ListeningService::class.java).also {
                it.action = ListeningService.ACTION_PLAY
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
            val play = Intent(this, ListeningService::class.java).also {
                it.action = ListeningService.ACTION_START
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
            val play = Intent(this, ListeningService::class.java).also {
                it.action = ListeningService.ACTION_EXIT
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
