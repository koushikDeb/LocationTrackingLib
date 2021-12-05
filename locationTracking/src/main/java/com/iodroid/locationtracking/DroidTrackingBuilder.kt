package com.iodroid.locationtracking

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

import com.iodroid.locationtracking.repo.Repository
import com.iodroid.locationtracking.repo.room.AppDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import com.iodroid.locationtracking.services.TrackingService
import com.iodroid.locationtracking.services.TrackingService.Companion.isServiceRunning
import com.iodroid.locationtracking.utils.LocationRequestBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.OffsetDateTime
import java.time.ZoneId

class DroidTracking(builder: Builder) {

   private val context = builder.context
   private val userId = builder.userId
   private val alarmScheduleIntent = Intent(context, TrackingService::class.java)
   private val isDbEnabled: Boolean =  builder.isDbEnabled
   private val accuracy: Int =  builder.accuracy


  private val timeInterval:Long = builder.timeInterval
  private val displacementInterval: Float = builder.displacementInterval
  private val fastTimeInterval: Long = builder.fastTimeInterval

  class Builder(context: Application){
    var context:Application = context

    var userId:String = "userId"
    var isDbEnabled = false
    var accuracy = 50


    var timeInterval:Long = 1000*60
    var displacementInterval: Float = 1f
    var fastTimeInterval: Long = 100

    fun setDbEnabled(isDbEnabled: Boolean): Builder{
      this.isDbEnabled = isDbEnabled
      return this
    }
    fun setUserId(userId: String): Builder{
      this.userId = userId
      return this
    }
    fun setAccuracy(accuracy: Int): Builder{
      this.accuracy = accuracy
      return this
    }

    //To set the periodic time to update location
    fun setLocationTimeInterval(timeInterval: Long): Builder{
      this.timeInterval = timeInterval
      return this
    }

    fun setLocationFastTimeInterval(fastTimeInterval: Long): Builder{
      this.fastTimeInterval = fastTimeInterval
      return this
    }

    //To set the minimum distance to to update location
    fun setLocationDistanceInterval(displacementInterval: Float): Builder{
      this.displacementInterval = displacementInterval
      return this
    }


    fun build(): DroidTracking{
      return DroidTracking(this)
    }
  }

  fun startTracking() {
    DBUtils.appDatabase = AppDatabase.getInstance(this.context)
    DBUtils.isDbEnabled = this.isDbEnabled
    DBUtils.userId = this.userId
    DBUtils.accuracy = this.accuracy

    LocationRequestBuilder.timeInterval = this.timeInterval
    LocationRequestBuilder.fastTimeInterval = this.fastTimeInterval
    LocationRequestBuilder.displacementInterval = this.displacementInterval


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

  fun getServiceRunningStatus():Boolean
  {
    return isServiceRunning
  }

  suspend fun getAllLocationCount(): Int {
    return Repository.getTotalCount()
  }

  suspend fun getAllLocation(): List<UserTrackingEntity> {
    return  Repository.getAll()
  }

  suspend fun getPositionByDate(date:OffsetDateTime): List<UserTrackingEntity> {
    var enddate:OffsetDateTime = date.toLocalDate().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toOffsetDateTime()
    return getPositionByDate(date,enddate)
  }

  suspend fun getPositionByDate(startDate:OffsetDateTime,endDate:OffsetDateTime): List<UserTrackingEntity> {
    return Repository.getPositionBetweenDateTime(startDate,endDate)
  }

  suspend fun getAllLocationCount(startDateTime: OffsetDateTime,endDateTime: OffsetDateTime): List<UserTrackingEntity> {
    return Repository.getPositionBetweenDateTime(startDateTime,endDateTime)
  }


  private fun locationPermissionAvailable(): Boolean {
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
  }


}