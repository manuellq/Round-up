package com.mlcorrea.roundup.data.dto.input

import com.mlcorrea.roundup.data.dto.model.AmountDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 18/08/19
 */
@JsonClass(generateAdapter = true)
data class AddMoneySavingsBody(
    @Json(name = "amount")
    val amount: AmountDto
)