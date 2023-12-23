package com.dpfht.demonewscompose.feature_category.event_state

sealed class UIEvent {
  object Init: UIEvent()
  data class OnClickCategoryItem(val categoryName: String): UIEvent()
  object Refresh: UIEvent()
}
