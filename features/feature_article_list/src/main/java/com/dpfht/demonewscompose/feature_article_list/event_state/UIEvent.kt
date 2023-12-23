package com.dpfht.demonewscompose.feature_article_list.event_state

sealed class UIEvent {
  data class Init(val sourceId: String): UIEvent()
  data class OnClickArticleItem(val title: String, val url: String): UIEvent()
  object OnBackPressed: UIEvent()
  object Refresh: UIEvent()
}
