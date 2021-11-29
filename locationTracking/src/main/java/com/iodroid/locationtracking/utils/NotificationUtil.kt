package com.iodroid.locationtracking.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iodroid.locationtracking.R

class NotificationUtil(private val service: Service) {

  private var notificationManager:NotificationManagerCompat? =null

  fun showNotification() {
    val notification: Notification = getNotification(service.getString(R.string.notification_desc))
    notifyNotification(notification)
    service.startForeground(Constants.notificationId, notification)
  }

  fun notifyNotification(notification: Notification) {
    if (notificationManager == null) {
      notificationManager = NotificationManagerCompat.from(service)
    }
    notificationManager?.let {
      it.notify(Constants.notificationId, notification)
    }
  }


  fun getNotification(contentText:String): Notification {
    return NotificationCompat.Builder(service, Constants.CHANNEL_ID_LOCATION_TRACKING)
      .setSmallIcon(R.drawable.ic_my_location)
      .setContentTitle(contentText)
      .setContentText(service.getString(R.string.notification_title))
      .setPriority(NotificationCompat.PRIORITY_HIGH).build()
    //  .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm))
    // .setContentIntent(pendingIntentRing)
  }

  fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name: CharSequence = service.getString(R.string.notification_title)
      val description =  service.getString(R.string.notification_desc)
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel = NotificationChannel(Constants.CHANNEL_ID_LOCATION_TRACKING, name, importance)
      channel.description = description

      val notificationManager: NotificationManager = service.getSystemService(
        NotificationManager::class.java
      )
      notificationManager.createNotificationChannel(channel)
    }
  }

}