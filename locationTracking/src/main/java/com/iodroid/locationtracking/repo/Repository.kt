package com.iodroid.locationtracking.repo

import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.appDatabase
import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDBInitialized
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity

object Repository {
  suspend fun saveToDb(userTrackingData: UserTrackingEntity)
  {
   if (isDBInitialized) {
     appDatabase.getUserTrackingDao().insert(userTrackingData)
   }
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