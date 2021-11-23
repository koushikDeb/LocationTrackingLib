package com.iodroid.locationtracking.repo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "UserTrackingEntity")
data class UserTrackingEntity(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,
  val userID: String?,
  val dateTime: OffsetDateTime?,
  val latitude: Double,
  val longitude: Double,
  )
