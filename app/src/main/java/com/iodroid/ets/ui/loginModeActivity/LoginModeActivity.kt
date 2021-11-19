package com.iodroid.ets.ui.loginModeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View

import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModel
import com.iodroid.ets.R
import com.iodroid.ets.databinding.ActivityLoginModeBinding

import com.iodroid.ets.ui.base.BaseActivity


class LoginModeActivity : BaseActivity<ViewModel, ActivityLoginModeBinding>() {


  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
  }

  override fun isFullScreen(): Boolean = true

  override fun getInflatedBinding(): ActivityLoginModeBinding = ActivityLoginModeBinding.inflate(layoutInflater)

  override fun getViewModelClass(): Class<ViewModel>? = null

  override fun created() {
   setupListners()
  }

  private fun setupListners(){
    binding.btnLogin.setOnClickListener(View.OnClickListener {
//      startActivity(Intent(this, SignIn::class.java))
      finish()
    })
    binding.btnSignup.setOnClickListener(View.OnClickListener {
//      startActivity(Intent(this, SignUp::class.java))
      finish()
    })
  }

}