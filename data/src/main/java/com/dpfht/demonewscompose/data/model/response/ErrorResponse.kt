package com.dpfht.demonewscompose.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(

  @SerializedName("status")
  val status: String? = null,
  @SerializedName("code")
  val code: String? = null,
  @SerializedName("message")
  val message: String? = null
)
