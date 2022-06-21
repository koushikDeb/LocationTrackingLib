package com.iodroid.locationtracking.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import com.iodroid.locationtracking.utils.NotificationUtil

import com.google.android.gms.location.*
import com.iodroid.locationtracking.ServiceBroadCastReceiver
import com.iodroid.locationtracking.repo.LocationDataSource
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import com.iodroid.locationtracking.utils.Constants
import com.iodroid.locationtracking.utils.Constants.BROADCAST_START_TRACKING
import com.iodroid.locationtracking.utils.Constants.BROADCAST_STOP_TRACKING
import com.iodroid.locationtracking.utils.Constants.notificationId

import com.iodroid.locationtracking.utils.LocationRequestBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime


class TrackingService : Service() {

  companion object{
    var isServiceRunning:Boolean = false
  }
  private val broadcastReceiver = ServiceBroadCastReceiver()

  private var fusedLocationClient: FusedLocationProviderClient? = null
  private val notificationUtil=  NotificationUtil(this)

  override fun onCreate() {
    super.onCreate()
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    registerReceiver()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    isServiceRunning = true
    notificationUtil.createNotificationChannel()
    startForeground(Constants.notificationId, notificationUtil.showNotification())
    startTracking()
    return START_STICKY
  }

  @SuppressLint("MissingPermission")
  private fun startTracking() {
    fusedLocationClient?.requestLocationUpdates(
      LocationRequestBuilder.buildLocationRequest(),
      LocationDataSource.getLocationCallBack {location,count ->
       locationUpdated(location,count)
      },
      null
    )
  }


  private fun locationUpdated(location: Location,count:Int) {
      notificationUtil.notifyNotification(notificationUtil.getNotification("total location $count "))
  }


  private fun registerReceiver(){
    val filter = IntentFilter()
    filter.addAction(BROADCAST_START_TRACKING)
    filter.addAction(BROADCAST_STOP_TRACKING)
    registerReceiver(broadcastReceiver,filter)
  }

  override fun onDestroy() {
    isServiceRunning = false
    super.onDestroy()
    unregisterReceiver(broadcastReceiver)
    stopSelf()
   // sendBroadcast(Intent().setAction(BROADCAST_START_TRACKING))
  }

  override fun onTaskRemoved(rootIntent: Intent?) {
    val restartServiceIntent = Intent(applicationContext, TrackingService::class.java).also {
      it.setPackage(packageName)
    };
    val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, notificationId, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
    applicationContext.getSystemService(ALARM_SERVICE);
    val alarmService: AlarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager;
    alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }
}