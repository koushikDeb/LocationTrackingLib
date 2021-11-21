package com.iodroid.locationtracking.utils

import android.annotation.SuppressLint
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult


object LocationRequestBuilder {

  @SuppressLint("RestrictedApi")
  fun buildLocationRequest(): LocationRequest {
   return LocationRequest().apply {
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      interval = 2*1000*60
      fastestInterval = 100
      smallestDisplacement = 1f
    }
  }

}