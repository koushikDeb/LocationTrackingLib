package com.iodroid.locationtracking.repo

import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.iodroid.locationtracking.repo.Repository.getTotalCount
import com.iodroid.locationtracking.repo.Repository.saveToDb
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDbEnabled
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.userId
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import com.iodroid.locationtracking.utils.Constants.TAG
import com.iodroid.locationtracking.utils.Constants.scope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

object LocationDataSource {

  fun getLocationCallBack(locationUpdated:(location:Location, count:Int) -> Unit)
    : LocationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult) {
        for (location in locationResult.locations) {

          if (location.accuracy < DBUtils.accuracy) {
            val userTrackingData = UserTrackingEntity(
              userID = userId,
              dateTime = OffsetDateTime.now(),
              latitude = location.latitude,
              longitude = location.longitude
            )
            scope.launch {
              if (isDbEnabled) {
                saveToDb(userTrackingData)
                locationUpdated(location, getTotalCount())
                Log.d(TAG, "save to db with ${location.latitude}  ${location.longitude}")
              }
            }
          }
        }
      }
    }

}