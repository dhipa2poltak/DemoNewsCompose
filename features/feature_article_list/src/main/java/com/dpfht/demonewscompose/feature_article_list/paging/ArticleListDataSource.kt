package com.dpfht.demonewscompose.feature_article_list.paging

import androidx.compose.runtime.MutableState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dpfht.demonewscompose.domain.entity.ArticleEntity
import com.dpfht.demonewscompose.domain.entity.Result
import com.dpfht.demonewscompose.domain.usecase.GetArticlesUseCase
import com.dpfht.demonewscompose.feature_article_list.event_state.UIState
import com.dpfht.demonewscompose.framework.Config
import javax.inject.Inject

class ArticleListDataSource @Inject constructor(
  private val getArticlesUseCase: GetArticlesUseCase,
) : PagingSource<Int, ArticleEntity>() {

  lateinit var uiState: MutableState<UIState>
  var sourceId: String? = null

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<ArticleEntity>()
      var prevKey: Int? = null
      var nextKey: Int? = null

      uiState.value = uiState.value.copy(isLoading = true, errorMessage = "")

      sourceId?.let {
        when (val result = getArticlesUseCase(it, null, currentLoadingPageKey, Config.DEFAULT_PAGE_SIZE)) {
          is Result.Success -> {
            arrList.addAll(result.value.articles)
            prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            nextKey = if (result.value.articles.isEmpty()) null else currentLoadingPageKey + 1
          }
          is Result.Error -> {
            throw Exception(result.message)
          }
        }
      }

      uiState.value = uiState.value.copy(isLoading = false)

      return LoadResult.Page(
        data = arrList,
        prevKey = prevKey,
        nextKey = nextKey
      )
    } catch (e: Exception) {
      uiState.value = uiState.value.copy(isLoading = false, errorMessage = e.message ?: "")

      return LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
    return state.anchorPosition
  }
}
