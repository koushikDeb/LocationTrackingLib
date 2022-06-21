package com.iodroid.locationtracking.utils

import com.iodroid.locationtracking.repo.room.AppDatabase

object Constants {
  const val notificationId: Int = 1002
  const val CHANNEL_ID_LOCATION_TRACKING = "com.iodroid.location_tracking.start_location_tracking"

  const val TrackingDDB = "user_tracking.db"
  const val BROADCAST_START_TRACKING = "com.iodroid.location_tracking.start_service"
  const val BROADCAST_STOP_TRACKING = "com.iodroid.location_tracking.stop_service"
}