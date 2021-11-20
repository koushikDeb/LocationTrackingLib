package com.iodroid.locationtracking.repo

import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class HandelLocationData {
   var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult) {
        for (location in locationResult.locations) {
          val latitude: String = java.lang.String.valueOf(location.latitude)
          val longitude: String = java.lang.String.valueOf(location.longitude)
          Log.d("locations","$latitude and $longitude")
        }
      }
    }

}