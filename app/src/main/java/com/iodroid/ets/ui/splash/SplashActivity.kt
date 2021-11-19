package com.iodroid.ets.ui.splash


import android.view.Window
import androidx.lifecycle.ViewModel
import com.iodroid.ets.databinding.ActivitySplashScreenBinding
import com.iodroid.ets.ui.base.ActivityNavigator
import com.iodroid.ets.ui.base.BaseActivity
import com.iodroid.ets.ui.loginModeActivity.LoginModeActivity
import com.iodroid.ets.util.Utils


class SplashActivity: BaseActivity<ViewModel, ActivitySplashScreenBinding> () {


  override fun isFullScreen() = true


  override fun getInflatedBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

  override fun getViewModelClass(): Class<ViewModel>? = null

  override fun created() {
    Utils.executeAtDelay(2000) {
     ActivityNavigator.startActivity(LoginModeActivity::class.java,this,)
     finish()
    }
  }
}