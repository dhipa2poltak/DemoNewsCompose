package com.dpfht.demonewscompose.domain.repository

import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.domain.entity.SourceDomain

interface AppRepository {

  suspend fun getCategories(): List<CategoryEntity>

  suspend fun getSources(categoryName: String): SourceDomain

  suspend fun getArticles(sources: String, query: String?, page: Int, pageSize: Int): ArticlesDomain

}
