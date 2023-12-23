package com.dpfht.demonewscompose.feature_article

import androidx.lifecycle.ViewModel
import com.dpfht.demonewscompose.feature_article.event_state.UIEvent
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
  private val navigationService: NavigationService
): ViewModel() {

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
    }
  }
}
