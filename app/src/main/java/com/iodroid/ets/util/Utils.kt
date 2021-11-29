package com.iodroid.ets.util

import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import android.R
import android.content.Context
import android.text.Layout
import android.view.View

import android.widget.LinearLayout

import android.view.ViewGroup






object Utils {

  fun executeAtDelay(totalDelay:Long,finishCalled:()->Unit){
    object: CountDownTimer(totalDelay,1000){
      override fun onTick(millisUntilFinished: Long) {}
      override fun onFinish() {
      finishCalled()
      }
    }.start()

  }



  @BindingAdapter("visible")
  @JvmStatic
  fun View.visible(visible:Boolean) {
     this.visibility = if(visible)View.VISIBLE else View.GONE
  }

}