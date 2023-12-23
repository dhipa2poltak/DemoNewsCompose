package com.dpfht.demonewscompose.feature_source.event_state

import com.dpfht.demonewscompose.domain.entity.SourceEntity

data class UIState(
  val categoryName: String = "",
  val isLoading: Boolean = false,
  val sources: List<SourceEntity> = arrayListOf(),
  val errorMessage: String = ""
)
