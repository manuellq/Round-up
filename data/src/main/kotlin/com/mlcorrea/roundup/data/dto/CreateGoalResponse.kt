package com.mlcorrea.roundup.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class CreateGoalResponse(
    @Json(name = "savingsGoalUid")
    val savingsGoalUid: String?,
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "errors")
    val errors: List<String>?
)