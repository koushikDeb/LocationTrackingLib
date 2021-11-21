package com.iodroid.ets.ui.loginModeActivity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View

import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.iodroid.ets.App
import com.iodroid.ets.R
import com.iodroid.ets.databinding.ActivityLoginModeBinding

import com.iodroid.ets.ui.base.BaseActivity
import com.iodroid.ets.util.AppConstants
import com.iodroid.locationtracking.DroidTracking


class LoginModeActivity : BaseActivity<ViewModel, ActivityLoginModeBinding>() {


  override fun isFullScreen(): Boolean = true

  override fun getInflatedBinding(): ActivityLoginModeBinding = ActivityLoginModeBinding.inflate(layoutInflater)

  override fun getViewModelClass(): Class<ViewModel>? = null

  override fun created() {
   setupListners()
  }

  private fun setupListners(){
    binding.btnLogin.setOnClickListener(View.OnClickListener {
//      startActivity(Intent(this, SignIn::class.java))
      startTrackingModule()
      //finish()
    })
    binding.btnSignup.setOnClickListener(View.OnClickListener {
//      startActivity(Intent(this, SignUp::class.java))
      finish()
    })
  }

  private fun startTrackingModule() {
    if (locationPermissionAvailable()) {
      (application as App).myTracker.startTracking()
    } else {
      requestWritePermission()
    }
  }

  override fun destroyed() {
    (application as App).myTracker.stopTracking()
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

}