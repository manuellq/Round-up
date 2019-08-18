package com.mlcorrea.roundup.data.dto.input

import com.mlcorrea.roundup.data.dto.model.AmountDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class CreateGoalBody(
    @Json(name = "name")
    val name: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "target")
    val target: AmountDto,
    @Json(name = "base64EncodedPhoto")
    val base64EncodedPhoto: String
)