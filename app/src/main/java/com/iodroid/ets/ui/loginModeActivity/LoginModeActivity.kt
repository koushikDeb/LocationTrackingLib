package com.iodroid.ets.ui.loginModeActivity

import android.content.pm.PackageManager
import android.view.View
import android.widget.Button

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iodroid.ets.App
import com.iodroid.ets.R
import com.iodroid.ets.databinding.ActivityLoginModeBinding

import com.iodroid.ets.ui.base.BaseActivity
import com.iodroid.ets.util.AppConstants
import com.iodroid.ets.util.Utils
import com.iodroid.ets.util.Utils.visible

import com.iodroid.locationtracking.DroidTrackingBuilder
import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginModeActivity : BaseActivity<ViewModel, ActivityLoginModeBinding>() {


  private val userLocations: MutableLiveData<List<UserTrackingEntity>> by lazy {
    MutableLiveData<List<UserTrackingEntity>>()
  }

  private var trackerBuilder: DroidTrackingBuilder? = null

  override fun created() {

    trackerBuilder = tracker.build()
    setupListeners()
  }

  override fun onResume() {
    super.onResume()
    checkTrackingText()
  }

  private fun checkTrackingText() {
    binding.progressHorizontal.visible(true)
    Utils.executeAtDelay(500) {
      trackerBuilder?.let { trackerBuilder ->
        binding.btnStartTrack.text = if (!trackerBuilder.getServiceRunningStatus()) {
          getString(R.string.startTracking)
        } else {
          getString(R.string.stopTracking)
        }
      }
      binding.progressHorizontal.visible(false)
    }
  }

  private fun setupListeners() {
    trackerBuilder?.let { trackerBuilder ->
      binding.btnStartTrack.setOnClickListener(View.OnClickListener {
        if (trackerBuilder.getServiceRunningStatus()) {
          trackerBuilder.stopTracking()
          checkTrackingText()
        } else {
          startTrackingModule()
          checkTrackingText()
        }
      })
    }

    binding.btnClearDb.setOnClickListener {
      GlobalScope.launch {
        trackerBuilder?.clearLocations()
        userLocations.postValue(trackerBuilder?.getAllLocation())
      }
    }


    binding.btnGetAllLocations.setOnClickListener(View.OnClickListener {
      GlobalScope.launch {
        userLocations.postValue(trackerBuilder?.getAllLocation())
      }
    })

    userLocations.observe(this) { allLocations ->
      binding.tvDbLog.text = ""
      allLocations?.forEach { ust ->
        binding.tvDbLog.text = "${binding.tvDbLog.text} \n \n ${getFlattenedDta(ust)}"
      }
    }

  }

  private fun getFlattenedDta(ust: UserTrackingEntity): String {
    return "${ust.userID} | ${ust.dateTime} | ${ust.latitude} , ${ust.longitude} "
  }

  private fun startTrackingModule() {
    if (locationPermissionAvailable()) {
      trackerBuilder?.startTracking()
    } else {
      requestWritePermission()
    }
  }

  override fun destroyed() {
    //   tracker.build().stopTracking()
  }

  private fun locationPermissionAvailable(): Boolean {
    return ContextCompat.checkSelfPermission(
      this,
      android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
  }

  private fun requestWritePermission() {
    ActivityCompat.requestPermissions(
      this,
      arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
      ),
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

  override fun getInflatedBinding(): ActivityLoginModeBinding =
    ActivityLoginModeBinding.inflate(layoutInflater)

  override fun getViewModelClass(): Class<ViewModel>? = null


}