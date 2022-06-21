package com.iodroid.locationtracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.iodroid.locationtracking.services.TrackingService
import com.iodroid.locationtracking.utils.Constants.BROADCAST_START_TRACKING
import com.iodroid.locationtracking.utils.Constants.BROADCAST_STOP_TRACKING

class ServiceBroadCastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    context?.let { con ->
      intent?.let { intent ->
        val locationTrackingServiceIntent = Intent(con, TrackingService::class.java)
        when {
          intent.action.equals(BROADCAST_START_TRACKING) -> {
            startService(con, locationTrackingServiceIntent)
          }
          intent.action.equals(BROADCAST_STOP_TRACKING) -> {
            stopService(locationTrackingServiceIntent,con)
          }
          else -> {
            Toast.makeText(con,"Error processing intent",Toast.LENGTH_LONG).show()
          }
        }
      }

    }
  }

  private fun stopService(locationTrackingServiceIntent: Intent, con: Context){

   con.stopService(locationTrackingServiceIntent)
  }

  private fun startService(
    con: Context,
    locationTrackingServiceIntent: Intent
  ) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    con.startForegroundService(locationTrackingServiceIntent)
  } else {
    con.startService(locationTrackingServiceIntent)
  }
}

