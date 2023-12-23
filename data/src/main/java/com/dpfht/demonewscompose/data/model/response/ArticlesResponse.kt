package com.dpfht.demonewscompose.data.model.response

import androidx.annotation.Keep
import com.dpfht.demonewscompose.data.model.Article
import com.dpfht.demonewscompose.data.model.toDomain
import com.dpfht.demonewscompose.domain.entity.ArticlesDomain
import com.google.gson.annotations.SerializedName

@Keep
data class ArticlesResponse(
  @SerializedName("status")
  val status: String?,
  @SerializedName("totalResults")
  val totalResults: Int?,
  @SerializedName("articles")
  val articles: List<Article>?
)

fun ArticlesResponse.toDomain(): ArticlesDomain {
  return ArticlesDomain(
    articles = this.articles?.map { it.toDomain() } ?: listOf()
  )
}
