package com.iodroid.locationtracking.repo.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import java.time.OffsetDateTime

@Dao
interface UserTrackingDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(userTrackingList: List<UserTrackingEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(userTrackingData: UserTrackingEntity)

  @Query("SELECT * FROM UserTrackingEntity WHERE date(dateTime) = :date ")
  suspend fun getPositionByDate(date: OffsetDateTime): List<UserTrackingEntity>

  @Query("SELECT * FROM UserTrackingEntity WHERE datetime(dateTime) >= :startDateTime and  datetime(dateTime) <= :endDateTime")
  suspend fun getPositionBetweenTime(startDateTime: OffsetDateTime,endDateTime:OffsetDateTime): List<UserTrackingEntity>

  @Query("DELETE FROM UserTrackingEntity")
  suspend fun clearAllLocations()


  @Query("SELECT * FROM UserTrackingEntity")
  suspend fun getAll(): List<UserTrackingEntity>
}