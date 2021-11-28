package com.iodroid.ets.ui.loginModeActivity

import android.content.pm.PackageManager
import android.view.View

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.iodroid.ets.App
import com.iodroid.ets.databinding.ActivityLoginModeBinding

import com.iodroid.ets.ui.base.BaseActivity
import com.iodroid.ets.util.AppConstants
import com.iodroid.locationtracking.DroidTrackingBuilder


class LoginModeActivity : BaseActivity<ViewModel, ActivityLoginModeBinding>() {


  override fun created() {
    setupListeners()
    tracker.setUserId("anyUserid")
  }

  private fun setupListeners(){
    binding.btnLogin.setOnClickListener(View.OnClickListener {
      startTrackingModule()
    })

    binding.btnSignup.setOnClickListener(View.OnClickListener {
      tracker.build().clearLocations()
    })
  }

  private fun startTrackingModule() {
    if (locationPermissionAvailable()) {
      tracker.build().startTracking()
    } else {
      requestWritePermission()
    }
  }

  override fun destroyed() {
   //   tracker.build().stopTracking()
  }

  private fun locationPermissionAvailable(): Boolean {
    return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
  }

  private fun requestWritePermission() {
    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),
      AppConstants.PERMISSION_LOCATION_REQUEST_CODE
    )
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == AppConstants.PERMISSION_LOCATION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startTrackingModule()
    }
  }



  override fun isFullScreen(): Boolean = true

  override fun getInflatedBinding(): ActivityLoginModeBinding = ActivityLoginModeBinding.inflate(layoutInflater)

  override fun getViewModelClass(): Class<ViewModel>? = null


}