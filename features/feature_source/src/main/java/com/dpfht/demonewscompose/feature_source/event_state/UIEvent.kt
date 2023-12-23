package com.dpfht.demonewscompose.feature_source.event_state

sealed class UIEvent {
  data class Init(val categoryName: String): UIEvent()
  data class OnClickSourceItem(val sourceId: String, val sourceName: String): UIEvent()
  object OnBackPressed: UIEvent()
  object Refresh: UIEvent()
}
