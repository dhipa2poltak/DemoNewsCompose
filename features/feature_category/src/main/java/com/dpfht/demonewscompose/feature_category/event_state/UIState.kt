package com.dpfht.demonewscompose.feature_category.event_state

import com.dpfht.demonewscompose.domain.entity.CategoryEntity

data class UIState(
  val isLoading: Boolean = false,
  val categories: List<CategoryEntity> = arrayListOf(),
  val errorMessage: String = ""
)
