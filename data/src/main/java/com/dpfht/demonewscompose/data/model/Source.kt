package com.dpfht.demonewscompose.data.model

import androidx.annotation.Keep
import com.dpfht.demonewscompose.domain.entity.SourceEntity
import com.google.gson.annotations.SerializedName

@Keep
data class Source(
  @SerializedName("id")
  val id: String?,
  @SerializedName("name")
  val name: String?,
  @SerializedName("description")
  val description: String?,
  @SerializedName("url")
  val url: String?,
  @SerializedName("category")
  val category: String?,
  @SerializedName("language")
  val language: String?,
  @SerializedName("country")
  val country: String?
)

fun Source.toDomain(): SourceEntity {
  return SourceEntity(
    id = this.id ?: "",
    name = this.name ?: "",
    description = this.description ?: "",
    url = this.url ?: "",
    category = this.category ?: ""
  )
}
