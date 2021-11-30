package com.iodroid.ets

import android.app.Application
import com.iodroid.locationtracking.DroidTrackingBuilder

class App: Application() {

  lateinit var myTracker:DroidTrackingBuilder.Builder;
  override fun onCreate() {
    super.onCreate()
    myTracker = getBuilder()


  }
  private fun getBuilder() :DroidTrackingBuilder.Builder{

    return DroidTrackingBuilder.Builder(this)
      .setDbEnabled(true)
      .setUserId("anyUserid")
      .setLocationDistanceInterval(0.1f)
      .setLocationFastTimeInterval(10)
      .setLocationTimeInterval(100)
  }
}