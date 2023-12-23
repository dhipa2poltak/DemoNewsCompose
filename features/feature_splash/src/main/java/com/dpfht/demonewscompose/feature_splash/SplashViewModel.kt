package com.dpfht.demonewscompose.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demonewscompose.framework.Config
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val navigationService: NavigationService
): ViewModel() {

  fun start() {
    viewModelScope.launch {
      delay(Config.DELAY_SPLASH)
      navigationService.navigate(Screen.Category)
    }
  }
}
