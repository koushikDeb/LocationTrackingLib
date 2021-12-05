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
import com.iodroid.locationtracking.DroidTracking

import com.iodroid.locationtracking.repo.room.entity.UserTrackingEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class LoginModeActivity : BaseActivity<ViewModel, ActivityLoginModeBinding>() {


  private val userLocations: MutableLiveData<List<UserTrackingEntity>> by lazy {
    MutableLiveData<List<UserTrackingEntity>>()
  }

  private var tracker: DroidTracking? = null

  override fun created() {

    tracker = trackerBuilder.build()
    setupListeners()
  }

  override fun onResume() {
    super.onResume()
    checkTrackingText()
  }

  private fun checkTrackingText() {
    binding.progressHorizontal.visible(true)
    Utils.executeAtDelay(500) {
      tracker?.let { tracker ->
        binding.btnStartTrack.text = if (!tracker.getServiceRunningStatus()) {
          getString(R.string.startTracking)
        } else {
          getString(R.string.stopTracking)
        }
      }
      binding.progressHorizontal.visible(false)
    }
  }

  private fun setupListeners() {
    tracker?.let { tracker ->
      binding.btnStartTrack.setOnClickListener(View.OnClickListener {
        if (tracker.getServiceRunningStatus()) {
          tracker.stopTracking()
          checkTrackingText()
        } else {
          startTrackingModule()
          checkTrackingText()
        }
      })
    }

    binding.btnClearDb.setOnClickListener {
      GlobalScope.launch {
        tracker?.clearLocations()
      }
    }


    binding.btnGetAllLocations.setOnClickListener(View.OnClickListener {
      GlobalScope.launch {
        val offsetDateTime: OffsetDateTime =
          LocalDate.parse(
            "2021-12-04",
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
          ).atStartOfDay().atZone(ZoneId.systemDefault()).toOffsetDateTime()

        userLocations.postValue(tracker?.getPositionByDate(offsetDateTime))
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
      tracker?.startTracking()
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