package com.dpfht.demonewscompose.feature_article_list.event_state

import androidx.paging.PagingData
import com.dpfht.demonewscompose.domain.entity.ArticleEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class UIState(
  val sourceId: String = "",
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val articlesState: MutableStateFlow<PagingData<ArticleEntity>> = MutableStateFlow(value = PagingData.empty()),
  val errorMessage: String = ""
)
