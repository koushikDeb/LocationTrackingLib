package com.iodroid.locationtracking.repo.room.dbUtils

import com.iodroid.locationtracking.repo.room.AppDatabase

object DBUtils {
  var isDbEnabled: Boolean = false
  lateinit var appDatabase: AppDatabase
  var userID:String = "uid"
}
