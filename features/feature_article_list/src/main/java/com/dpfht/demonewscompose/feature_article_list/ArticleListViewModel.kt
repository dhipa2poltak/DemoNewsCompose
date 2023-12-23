package com.dpfht.demonewscompose.feature_article_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.demonewscompose.feature_article_list.event_state.UIEvent
import com.dpfht.demonewscompose.feature_article_list.event_state.UIState
import com.dpfht.demonewscompose.feature_article_list.paging.ArticleListDataSource
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val articleListDataSource: ArticleListDataSource,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(sourceId = event.sourceId)
        start()
      }
      is UIEvent.OnClickArticleItem -> {
        navigationService.navigate(Screen.Article(event.title, event.url))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(
          isLoaded = false,
          isLoading = false,
          articlesState = MutableStateFlow(value = PagingData.empty()),
          errorMessage = ""
        )
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.sourceId != "" && !_uiState.value.isLoaded) {
      loadArticles(_uiState.value.sourceId)
    }
  }

  private fun loadArticles(sourceId: String) {
    articleListDataSource.sourceId = sourceId
    articleListDataSource.uiState = _uiState

    val pager = Pager(PagingConfig(pageSize = 20)) {
      articleListDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        _uiState.value = _uiState.value.copy(isLoaded = true)
        _uiState.value.articlesState.value = it
      }
    }
  }

  fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }
}
