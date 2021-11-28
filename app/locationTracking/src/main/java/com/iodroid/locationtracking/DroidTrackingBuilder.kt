package com.iodroid.locationtracking

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.iodroid.locationtracking.repo.Repository
import com.iodroid.locationtracking.repo.room.AppDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.services.TrackingService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DroidTrackingBuilder(builder: Builder) {

   private val context = builder.context
   private val userId = builder.userId
   private val alarmScheduleIntent = Intent(context, TrackingService::class.java)
   private val isDbEnabled: Boolean =  builder.isDbEnabled

  class Builder(context: Application){
    var context:Application = context

  class Builder(context: Application){
    var context:Application = context

    var userId:String = "userId"
    var isDbEnabled = false



    fun setDbEnabled(isDbEnabled: Boolean): Builder{
      this.isDbEnabled = isDbEnabled
      return this
    }
    fun setUserId(userId: String): Builder{
      this.userId = userId
      return this
    }


    fun build(): DroidTrackingBuilder{
      return DroidTrackingBuilder(this)
    }
  }

  fun startTracking() {
    DBUtils.appDatabase = AppDatabase.getInstance(this.context)
    DBUtils.isDbEnabled = this.isDbEnabled

    DBUtils.userId = this.userId
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

  fun clearLocations() {
    GlobalScope.launch {
      Repository.clearDb()
    }
  }


  fun getTotalCount() {
    GlobalScope.launch {
      Repository.getTotalCount()
    }
  }

  private fun locationPermissionAvailable(): Boolean {
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
  }


}