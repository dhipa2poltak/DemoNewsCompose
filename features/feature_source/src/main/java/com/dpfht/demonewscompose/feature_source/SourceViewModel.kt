package com.dpfht.demonewscompose.feature_source

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demonewscompose.domain.entity.Result.Success
import com.dpfht.demonewscompose.domain.entity.Result.Error
import com.dpfht.demonewscompose.domain.entity.SourceEntity
import com.dpfht.demonewscompose.domain.usecase.GetSourcesUseCase
import com.dpfht.demonewscompose.feature_source.event_state.UIEvent
import com.dpfht.demonewscompose.feature_source.event_state.UIState
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getSourcesUseCase: GetSourcesUseCase,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(categoryName = event.categoryName)
        start()
      }
      is UIEvent.OnClickSourceItem -> {
        navigationService.navigate(Screen.ArticleList(event.sourceId, event.sourceName, _uiState.value.categoryName))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(isLoading = false, sources = arrayListOf(), errorMessage = "")
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.sources.isNotEmpty()) {
      return
    }

    loadSources()
  }

  private fun loadSources() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true)
      when (val result = getSourcesUseCase(_uiState.value.categoryName)) {
        is Success -> {
          onSuccessLoadSources(result.value.sources)
        }
        is Error -> {
          onErrorLoadSources(result.message)
        }
      }
    }
  }

  private fun onSuccessLoadSources(sources: List<SourceEntity>) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      sources = sources
    )
  }

  private fun onErrorLoadSources(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
