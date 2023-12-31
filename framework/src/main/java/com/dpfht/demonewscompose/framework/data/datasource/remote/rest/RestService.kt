package com.dpfht.demonewscompose.framework.data.datasource.remote.rest

import com.dpfht.demonewscompose.data.model.response.ArticlesResponse
import com.dpfht.demonewscompose.data.model.response.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

  @GET("top-headlines")
  suspend fun getArticles(
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    @Query("sources") sources: String,
    @Query("q") query: String?
  ): ArticlesResponse

  @GET("top-headlines/sources")
  suspend fun getSources(@Query("category") category: String): SourceResponse
}
