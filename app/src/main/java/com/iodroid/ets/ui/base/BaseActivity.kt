package com.iodroid.ets.ui.base

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.iodroid.ets.databinding.ActivityLoginModeBinding
import java.util.zip.Inflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : ViewModel, B : ViewBinding>() : AppCompatActivity() {

  lateinit var binding: B
  lateinit var viewModel: VM

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
    binding = getInflatedBinding()
    setContentView(binding.root)
    initViewModel()
    if (isFullScreen()) {
      fullScreen()
    }
    created()
  }

  abstract fun isFullScreen(): Boolean

  private fun fullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      window.insetsController?.hide(WindowInsets.Type.statusBars())
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