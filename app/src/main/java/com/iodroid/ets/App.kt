package com.iodroid.ets

import android.app.Application
import com.iodroid.locationtracking.DroidTracking

class App: Application() {

  lateinit var myTracker: DroidTracking.Builder

  override fun onCreate() {
    super.onCreate()
    myTracker = getBuilder()
  }

  private fun getBuilder(): DroidTracking.Builder = DroidTracking.Builder(this)
      .setDbEnabled(true)
      .setUserId("anyUserid")
      .setLocationDistanceInterval(0.1f)
      .setLocationFastTimeInterval(10)
      .setLocationTimeInterval(1000)
      .setAccuracy(20)
}