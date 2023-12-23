package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.Result
import com.dpfht.demonewscompose.domain.repository.AppRepository

class GetArticlesUseCaseImpl(
  private val appRepository: AppRepository
): GetArticlesUseCase {
  override suspend operator fun invoke(
    sources: String,
    query: String?,
    page: Int,
    pageSize: Int
  ): Result<ArticlesDomain> {
    return try {
      Result.Success(appRepository.getArticles(sources, query, page, pageSize))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
