package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.Result
import com.dpfht.demonewscompose.domain.entity.SourceDomain

interface GetSourcesUseCase {

  suspend operator fun invoke(categoryName: String): Result<SourceDomain>
}
