package com.iodroid.ets.ui.base

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.iodroid.ets.databinding.ActivityLoginModeBinding
import java.util.zip.Inflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.iodroid.ets.App
import com.iodroid.locationtracking.DroidTracking

abstract class BaseActivity<VM : ViewModel, B : ViewBinding>() : AppCompatActivity() {

  lateinit var binding: B
  lateinit var viewModel: VM
  lateinit var trackerBuilder: DroidTracking.Builder
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = getInflatedBinding()
    initViewModel()
    setContentView(binding.root)
    if (isFullScreen()) {
      fullScreen()
    }
    trackerBuilder = (application as App).myTracker
    created()
  }

  override fun onDestroy() {
    super.onDestroy()
    destroyed()
  }

  abstract fun destroyed()

  abstract fun isFullScreen(): Boolean

  private fun fullScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      val controller =window.insetsController
      if(controller != null) {
        controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
      }
    } else {
      window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
      )
    }

  }

  private fun initViewModel() {
    var viewModelClass  =  getViewModelClass()
    viewModelClass?.let {
      viewModel = ViewModelProvider(this).get(it)
    }
  }

  abstract fun getInflatedBinding(): B

  abstract fun getViewModelClass(): Class<VM>?

  abstract fun created()

}