package com.dpfht.demonewscompose.data.datasource

import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.SourceDomain

interface RemoteDataSource {

  suspend fun getSources(categoryName: String): SourceDomain

  suspend fun getArticles(sources: String, query: String?, page: Int, pageSize: Int): ArticlesDomain

}
