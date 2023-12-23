package com.dpfht.demonewscompose.framework.data.datasource.local

import android.content.Context
import com.dpfht.demonewscompose.data.datasource.LocalDataSource
import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.framework.R

class LocalDataSourceImpl(
  private val context: Context,
): LocalDataSource {

  override suspend fun getCategories(): List<CategoryEntity> {
    return context.resources.getStringArray(R.array.arr_news_categories).toList().map { CategoryEntity(it) }
  }
}
