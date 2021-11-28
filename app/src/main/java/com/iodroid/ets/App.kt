package com.iodroid.ets

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iodroid.locationtracking.DroidTrackingBuilder

class App: Application() {

  lateinit var myTracker:DroidTrackingBuilder.Builder;
  override fun onCreate() {
    super.onCreate()
    myTracker = getBuilder()


  }
  private fun getBuilder() :DroidTrackingBuilder.Builder{
    val db = Firebase.firestore
    return DroidTrackingBuilder.Builder(this)
      .setDbEnabled(true)
      .setFirebaseEnabled(db)
  }
}