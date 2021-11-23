package com.iodroid.locationtracking.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.iodroid.locationtracking.utils.NotificationUtil

import com.google.android.gms.location.*
import com.iodroid.locationtracking.repo.LocationDataSource

import com.iodroid.locationtracking.utils.LocationRequestBuilder


class TrackingService : Service() {

  private var fusedLocationClient: FusedLocationProviderClient? = null
  private val notificationUtil=  NotificationUtil(this)

  override fun onCreate() {
    super.onCreate()
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    notificationUtil.createNotificationChannel()
    notificationUtil.showNotification()
    startTracking()
    return START_STICKY
  }

  @SuppressLint("MissingPermission")
  private fun startTracking() {
    fusedLocationClient?.requestLocationUpdates(
      LocationRequestBuilder.buildLocationRequest(),
      LocationDataSource().locationCallback,
      null
    )
  }



  override fun onDestroy() {
    super.onDestroy()
    stopSelf()
  }


  override fun onBind(intent: Intent?): IBinder? {
    return null
  }
}