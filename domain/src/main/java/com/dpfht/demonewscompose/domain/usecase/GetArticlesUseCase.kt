package com.dpfht.demonewscompose.domain.usecase

import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.Result

interface GetArticlesUseCase {

  suspend operator fun invoke(sources: String, query: String?, page: Int, pageSize: Int): Result<ArticlesDomain>
}
