package com.iodroid.locationtracking.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object Constants {
  const val TAG = "trackerLogTag"
  const val notificationId: Int = 1002
  const val CHANNEL_ID_LOCATION_TRACKING = "com.iodroid.location_tracking.start_alarm"
  const val TrackingDDB = "user_tracking.db"

  val scope = CoroutineScope(Dispatchers.IO)
}