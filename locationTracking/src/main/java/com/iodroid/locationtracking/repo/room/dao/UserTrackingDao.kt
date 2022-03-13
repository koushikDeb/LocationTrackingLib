package com.iodroid.locationtracking.repo.room.dao

import androidx.room.*
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

  @Query("SELECT * FROM UserTrackingEntity WHERE dateTime >= :startDateTime AND  dateTime < :endDateTime")
  suspend fun getPositionBetweenTime(startDateTime: OffsetDateTime,endDateTime:OffsetDateTime): List<UserTrackingEntity>

  @Query("DELETE FROM UserTrackingEntity")
  suspend fun clearAllLocations()

  @Delete
  suspend fun deleteSpecific(item:UserTrackingEntity)

  @Query("DELETE FROM UserTrackingEntity WHERE id = :id")
  suspend fun deleteById(id: Int)

  @Query("DELETE FROM UserTrackingEntity WHERE userID = :id")
  suspend fun deleteByUserId(id: String)

  @Query("DELETE FROM UserTrackingEntity WHERE dateTime >= :startDateTime AND  dateTime < :endDateTime")
  suspend fun deleteByDate(startDateTime: OffsetDateTime,endDateTime:OffsetDateTime)


  @Query("SELECT * FROM UserTrackingEntity")
  suspend fun getAll(): List<UserTrackingEntity>
}