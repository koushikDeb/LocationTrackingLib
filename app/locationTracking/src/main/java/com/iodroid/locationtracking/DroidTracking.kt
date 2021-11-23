package com.iodroid.locationtracking

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.iodroid.locationtracking.repo.room.AppDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.appDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDbEnabled
import com.iodroid.locationtracking.services.TrackingService
import com.iodroid.locationtracking.utils.Constants.TrackingDDB
import java.lang.Exception


open class DroidTracking(val context: Context) {
   private val alarmScheduleIntent = Intent(context, TrackingService::class.java)

   fun setDBEnabled(status:Boolean){
      isDbEnabled = status
   }
    fun startTracking() {
       appDatabase = AppDatabase.getInstance(context)
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