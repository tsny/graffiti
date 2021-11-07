package com.example.graffiti

import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class utils {

//    private fun sendNotif() {
//        val n = NotificationCompat.Builder(this, channelID)
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle("test")
//            .setContentText("test text goes here")
//            .setDefaults(Notification.DEFAULT_ALL)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//        // random id
//        val notificationId = 123
//
//        with(NotificationManagerCompat.from(this)) {
//            notify(notificationId, n)
//        }
//    }


//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channel_description)
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel(channelID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//    }
}