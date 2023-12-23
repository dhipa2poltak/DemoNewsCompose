package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.domain.entity.Result

interface GetCategoriesUseCase {

  suspend operator fun invoke(): Result<List<CategoryEntity>>
}
