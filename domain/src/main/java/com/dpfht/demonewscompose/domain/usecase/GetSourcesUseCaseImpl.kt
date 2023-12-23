package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.Result
import com.dpfht.demonewscompose.domain.entity.SourceDomain
import com.dpfht.demonewscompose.domain.repository.AppRepository

class GetSourcesUseCaseImpl(
  private val appRepository: AppRepository
): GetSourcesUseCase {

  override suspend operator fun invoke(categoryName: String): Result<SourceDomain> {
    return try {
      Result.Success(appRepository.getSources(categoryName))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
