package com.iodroid.locationtracking.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.iodroid.locationtracking.repo.LocationDataSource
import com.iodroid.locationtracking.utils.Constants.TAG
import com.iodroid.locationtracking.utils.LocationRequestBuilder
import com.iodroid.locationtracking.utils.NotificationUtil

class TrackingService : Service() {

  companion object{
    private var _isServiceRunning: Boolean = false
    val isServiceRunning get() = _isServiceRunning
  }

  private var fusedLocationClient: FusedLocationProviderClient? = null
  private val notificationUtil = NotificationUtil(this)

  override fun onCreate() {
    super.onCreate()
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    _isServiceRunning = true
    notificationUtil.createNotificationChannel()
    notificationUtil.showNotification()
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
      Log.d(TAG, "locationUpdated: called $count")
      notificationUtil.notifyNotification(notificationUtil.getNotification("total location $count "))
  }

  override fun onDestroy() {
    _isServiceRunning = false
    super.onDestroy()
    stopSelf()
  }

  override fun onBind(intent: Intent?): IBinder? = null
}