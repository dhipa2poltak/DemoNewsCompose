package com.dpfht.demonewscompose.framework.data.datasource.remote

import android.content.Context
import com.dpfht.demonewscompose.data.datasource.RemoteDataSource
import com.dpfht.demonewscompose.data.model.response.ErrorResponse
import com.dpfht.demonewscompose.data.model.response.toDomain
import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.dpfht.demonewscompose.domain.entity.SourceDomain
import com.dpfht.demonewscompose.framework.R
import com.dpfht.demonewscompose.framework.data.datasource.remote.rest.RestService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getSources(categoryName: String): SourceDomain {
    return safeApiCall(Dispatchers.IO) { restService.getSources(categoryName) }.toDomain()
  }

  override suspend fun getArticles(
    sources: String,
    query: String?,
    page: Int,
    pageSize: Int
  ): ArticlesDomain {
    return safeApiCall(Dispatchers.IO) { restService.getArticles(
      sources = sources,
      query = query,
      page = page,
      pageSize = pageSize
    ) }.toDomain()
  }

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> Exception(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            val errorResponse = convertErrorBody(t)

            Exception(errorResponse?.message ?: context.getString(R.string.framework_text_error_http))
          }
          else -> {
            Exception(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
