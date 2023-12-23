package com.dpfht.demonewscompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication: Application() {

  companion object {
    lateinit var instance: TheApplication
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }
}
