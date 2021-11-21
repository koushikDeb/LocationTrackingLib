package com.iodroid.ets

import android.app.Application
import com.iodroid.locationtracking.DroidTracking

class App: Application() {

  open lateinit var myTracker:DroidTracking;
  override fun onCreate() {
    super.onCreate()
    myTracker = DroidTracking(applicationContext)
  }
}