package com.mlcorrea.roundup.data.dto.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class NetworkResponseDTO<T>(
    @Json(name = "results")
    val data: T?,
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "errors")
    val errors: List<String>?
)