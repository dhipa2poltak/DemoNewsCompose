package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.domain.entity.Result
import com.dpfht.demonewscompose.domain.repository.AppRepository

class GetCategoriesUseCaseImpl(
  private val appRepository: AppRepository
) : GetCategoriesUseCase {
  override suspend operator fun invoke(): Result<List<CategoryEntity>> {
    return try {
      Result.Success(appRepository.getCategories())
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
