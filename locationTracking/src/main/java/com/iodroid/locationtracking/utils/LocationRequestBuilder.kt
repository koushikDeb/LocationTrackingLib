package com.iodroid.locationtracking.utils

import com.google.android.gms.location.LocationRequest

object LocationRequestBuilder {

  var timeInterval:Long = 0
  var displacementInterval: Float = 0f
  var fastTimeInterval: Long = 0

  fun buildLocationRequest(): LocationRequest = LocationRequest.create().apply {
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      interval = timeInterval
      fastestInterval = fastTimeInterval
      smallestDisplacement = displacementInterval
    }

}