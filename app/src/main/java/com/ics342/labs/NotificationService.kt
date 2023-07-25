package com.ics342.labs

import android.Manifest.permission
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.util.UUID

class NotificationService: Service() {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(this)

    override fun onCreate() {
        super.onCreate()
        Log.d("onCreate", "after super constructor called")
        createNotificationChannel()
        Log.d("onCreate", "return from createNotificationChannel()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("onStartCommand", "in onStartCommand")
        // If permission has not been granted, stop the service and return from
        // onStartCommand
        if (ContextCompat.checkSelfPermission(
                this@NotificationService,
                permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            stopSelf()
            return START_NOT_STICKY
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        Log.d("onStartCommand", "Pending Intent")

       // Build notification

        //"Build and show notification"
        var builder = NotificationCompat.Builder(this, "LAB_8_CHANNEL_ID")
            .setSmallIcon(R.drawable.star)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        Log.d("onStartCommand", "builder")

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }

        Log.d("onStartCommand", "Leaving onStartCommand")

        return START_STICKY_COMPATIBILITY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // No need to implement for lab 8
        return null
    }

    private fun createNotificationChannel() {
        //"Create notification channel and register with the system"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "LAB_8_CHANNEL_ID"
        private const val NOTIFICATION_ID = 1234
    }
}
