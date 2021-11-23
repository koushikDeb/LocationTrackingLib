package com.iodroid.locationtracking.repo

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDbEnabled
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.userID
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.OffsetDateTime

class LocationDataSource {
   var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult) {
        for (location in locationResult.locations) {

          var userTrackingData = UserTrackingEntity(userID = userID,dateTime = OffsetDateTime.now(),latitude = location.latitude,longitude = location.longitude)
          GlobalScope.launch {
            if(isDbEnabled) {
              saveToDb(userTrackingData)
            }
          }
        }
      }
    }


  suspend fun saveToDb(userTrackingData: UserTrackingEntity)
  {
    DBUtils.appDatabase.getUserTrackingDao().insert(userTrackingData)
  }

}