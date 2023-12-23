package com.dpfht.demonewscompose.data.repository

import com.dpfht.demonewscompose.data.datasource.LocalDataSource
import com.dpfht.demonewscompose.data.datasource.RemoteDataSource
import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.domain.entity.SourceDomain
import com.dpfht.demonewscompose.domain.repository.AppRepository

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {
  override suspend fun getCategories(): List<CategoryEntity> {
    return localDataSource.getCategories()
  }

  override suspend fun getSources(categoryName: String): SourceDomain {
    return remoteDataSource.getSources(categoryName)
  }

  override suspend fun getArticles(
    sources: String,
    query: String?,
    page: Int,
    pageSize: Int
  ): ArticlesDomain {
    return remoteDataSource.getArticles(sources, query, page, pageSize)
  }
}
