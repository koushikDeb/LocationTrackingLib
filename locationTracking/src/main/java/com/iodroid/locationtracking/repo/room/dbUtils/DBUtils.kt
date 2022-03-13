package com.iodroid.locationtracking.repo.room.dbUtils

import com.iodroid.locationtracking.repo.room.AppDatabase

object DBUtils {
  var isDbEnabled: Boolean = false

  var isFirebaseEnabled: Boolean = false

  lateinit var appDatabase: AppDatabase
  var isDBInitialized = false
    get() = this::appDatabase.isInitialized

  var userId:String = "uid"
  var accuracy:Int = 50
}
