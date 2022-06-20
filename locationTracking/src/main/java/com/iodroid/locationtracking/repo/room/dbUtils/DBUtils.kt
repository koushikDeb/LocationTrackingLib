package com.iodroid.locationtracking.repo.room.dbUtils

import com.iodroid.locationtracking.repo.room.AppDatabase
import com.iodroid.locationtracking.repo.room.dao.UserTrackingDao

object DBUtils {
  var isDbEnabled: Boolean = false

  var isFirebaseEnabled: Boolean = false

  lateinit var appDatabase: AppDatabase
  val isDBInitialized get() = this::appDatabase.isInitialized

  var userId:String = "uid"
  var accuracy:Int = 50

  inline fun <T> isDbInit(onDbInit: (dao: UserTrackingDao) -> T): T? {
    return if(isDBInitialized) {
      onDbInit(appDatabase.getUserTrackingDao())
    } else null
  }
}
