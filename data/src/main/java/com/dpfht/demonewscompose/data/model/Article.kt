package com.dpfht.demonewscompose.data.model

import androidx.annotation.Keep
import com.dpfht.demonewscompose.domain.entity.ArticleEntity
import com.google.gson.annotations.SerializedName

@Keep
data class Article(
  @SerializedName("source")
  val source: PairSource?,
  @SerializedName("author")
  val author: String?,
  @SerializedName("title")
  val title: String?,
  @SerializedName("description")
  val description: String?,
  @SerializedName("url")
  val url: String?,
  @SerializedName("urlToImage")
  val image: String?,
  @SerializedName("publishedAt")
  val publishedAt: String?,
  @SerializedName("content")
  val content: String?
)

fun Article.toDomain(): ArticleEntity {
  return ArticleEntity(
    author = this.author ?: "",
    title = this.title ?: "",
    description = this.description ?: "",
    url = this.url ?: "",
    image = this.image ?: ""
  )
}
