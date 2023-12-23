package com.dpfht.demonewscompose.feature_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.domain.entity.Result.Error
import com.dpfht.demonewscompose.domain.entity.Result.Success
import com.dpfht.demonewscompose.domain.usecase.GetCategoriesUseCase
import com.dpfht.demonewscompose.feature_category.event_state.UIEvent
import com.dpfht.demonewscompose.feature_category.event_state.UIState
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getCategoriesUseCase: GetCategoriesUseCase,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.Init -> {
        start()
      }
      is UIEvent.OnClickCategoryItem -> {
        navigationService.navigate(Screen.Source(event.categoryName))
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(isLoading = false, categories = arrayListOf(), errorMessage = "")
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.categories.isNotEmpty()) {
      return
    }

    loadCategories()
  }

  private fun loadCategories() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true)
      when (val result = getCategoriesUseCase()) {
        is Success -> {
          onSuccessLoadCategories(result.value)
        }
        is Error -> {
          onErrorLoadCategories(result.message)
        }
      }
    }
  }

  private fun onSuccessLoadCategories(categories: List<CategoryEntity>) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      categories = categories
    )
  }

  private fun onErrorLoadCategories(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
