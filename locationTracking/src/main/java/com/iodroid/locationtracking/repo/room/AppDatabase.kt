package com.iodroid.locationtracking.repo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iodroid.locationtracking.repo.room.dao.UserTrackingDao
import com.iodroid.locationtracking.repo.room.dbUtils.DateTimeConverter
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import com.iodroid.locationtracking.utils.Constants.TrackingDDB


@Database(version = 1, entities = [UserTrackingEntity::class], exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {


  abstract fun getUserTrackingDao(): UserTrackingDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase =
      INSTANCE ?: synchronized(this) {
        INSTANCE
          ?: buildDatabase(context).also { INSTANCE = it }
      }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, TrackingDDB)
        .build()
  }
}