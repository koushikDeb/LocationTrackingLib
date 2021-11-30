package com.iodroid.locationtracking.utils

import android.annotation.SuppressLint
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult


object LocationRequestBuilder {

  var timeInterval:Long = 0
  var displacementInterval: Float = 0f
  var fastTimeInterval: Long = 0

  @SuppressLint("RestrictedApi")
  fun buildLocationRequest(): LocationRequest {
   return LocationRequest().apply {
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      interval = timeInterval
      fastestInterval = fastTimeInterval
      smallestDisplacement = displacementInterval
    }
  }

}