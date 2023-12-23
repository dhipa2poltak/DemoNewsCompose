package com.dpfht.demonewscompose.data.model.response

import androidx.annotation.Keep
import com.dpfht.demonewscompose.data.model.Source
import com.dpfht.demonewscompose.data.model.toDomain
import com.dpfht.demonewscompose.domain.entity.SourceDomain
import com.google.gson.annotations.SerializedName

@Keep
data class SourceResponse(
  @SerializedName("status")
  val status: String?,
  @SerializedName("sources")
  val sources: List<Source>?
)

fun SourceResponse.toDomain(): SourceDomain {
  return SourceDomain(
    sources = this.sources?.map { it.toDomain() } ?: listOf()
  )
}
