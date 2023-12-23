package com.dpfht.demonewscompose.data.model

import androidx.annotation.Keep
import com.dpfht.demonewscompose.domain.entity.PairSourceEntity
import com.google.gson.annotations.SerializedName

@Keep
data class PairSource(
  @SerializedName("id")
  val id: String?,
  @SerializedName("name")
  val name: String?
)

fun PairSource.toDomain(): PairSourceEntity {
  return PairSourceEntity(
    id = this.id ?: "",
    name = this.name ?: ""
  )
}
