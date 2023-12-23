package com.dpfht.demonewscompose.data.datasource

import com.dpfht.demonewscompose.domain.entity.CategoryEntity

interface LocalDataSource {

  suspend fun getCategories(): List<CategoryEntity>
}
