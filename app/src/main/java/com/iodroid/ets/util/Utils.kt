package com.iodroid.ets.util

import android.os.CountDownTimer

object Utils {

  fun executeAtDelay(totalDelay:Long,finishCalled:()->Unit){
    object: CountDownTimer(totalDelay,1000){
      override fun onTick(millisUntilFinished: Long) {
      }
      override fun onFinish() {
      finishCalled()
      }
    }.start()

  }
}