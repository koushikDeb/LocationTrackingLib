package com.iodroid.locationtracking.repo

import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.appDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDBInitialized
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import java.time.OffsetDateTime

object Repository {
  suspend fun saveToDb(userTrackingData: UserTrackingEntity)
  {
   if (isDBInitialized) {
     appDatabase.getUserTrackingDao().insert(userTrackingData)
   }
  }

  suspend fun getAll(): List<UserTrackingEntity> {
    if (isDBInitialized) {
      return appDatabase.getUserTrackingDao().getAll()
    }
    return emptyList()
  }

  suspend fun getPositionByDate(date:OffsetDateTime): List<UserTrackingEntity> {
    if (isDBInitialized) {
      return appDatabase.getUserTrackingDao().getPositionByDate(date)
    }
    return emptyList()
  }

  suspend fun getPositionBetweenDateTime(startDateTime: OffsetDateTime, endDateTime:OffsetDateTime): List<UserTrackingEntity> {
    if (isDBInitialized) {
      return appDatabase.getUserTrackingDao().getPositionBetweenTime(startDateTime,endDateTime)
    }
    return emptyList()
  }



  suspend fun getTotalCount(): Int {
    if (isDBInitialized) {
      return appDatabase.getUserTrackingDao().getAll().size
    }
    return 0
  }

  suspend fun clearDb(){
    if (isDBInitialized) {
      appDatabase.getUserTrackingDao().clearAllLocations()
    }
  }

}