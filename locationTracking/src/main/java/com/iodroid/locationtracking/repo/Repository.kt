package com.iodroid.locationtracking.repo

import com.iodroid.locationtracking.repo.room.dbUtils.DBUtils.isDbInit
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import java.time.OffsetDateTime

object Repository {

  suspend fun saveToDb(userTrackingData: UserTrackingEntity) =
    isDbInit { dao -> dao.insert(userTrackingData) }

  suspend fun getAll(): List<UserTrackingEntity> = isDbInit { dao -> dao.getAll() } ?: emptyList()

  suspend fun getPositionByDate(date:OffsetDateTime): List<UserTrackingEntity> =
    isDbInit { dao -> dao.getPositionByDate(date) } ?: emptyList()

  suspend fun getPositionBetweenDateTime(startDateTime: OffsetDateTime, endDateTime:OffsetDateTime): List<UserTrackingEntity> =
    isDbInit { dao -> dao.getPositionBetweenTime(startDateTime, endDateTime) } ?: emptyList()

  suspend fun getTotalCount(): Int = getAll().size

  suspend fun clearDb() = isDbInit { dao -> dao.clearAllLocations() }

  suspend fun clearLocationsByDate(startDateTime: OffsetDateTime,endDateTime:OffsetDateTime) =
    isDbInit { dao -> dao.deleteByDate(startDateTime, endDateTime) }

  suspend fun clearSpecificLocation(locationItem: UserTrackingEntity) =
    isDbInit { dao -> dao.deleteSpecific(locationItem) }

  suspend fun clearLocationByItemID(id: Int) = isDbInit { dao -> dao.deleteById(id) }

  suspend fun clearLocationByUserID(userId: String) = isDbInit { dao -> dao.deleteByUserId(userId) }

}