package com.iodroid.locationtracking

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.iodroid.locationtracking.services.TrackingService
import java.lang.Exception


open class DroidTracking(val context: Context) {
   private val alarmScheduleIntent = Intent(context, TrackingService::class.java)

    fun startTracking() {
       if (!locationPermissionAvailable()) {
          throw (Exception("Location Permission not available exception"))
       }

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          context.startForegroundService(alarmScheduleIntent)
       } else {
          context.startService(alarmScheduleIntent)
       }
    }

   fun stopTracking() {
      context.stopService(alarmScheduleIntent)
   }

   private fun locationPermissionAvailable(): Boolean {
      return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
   }



}