package com.mlcorrea.roundup.data.dto.model

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.domain.model.Amount
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class AmountDto(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "minorUnits")
    val minorUnits: String
) : BaseDto<Amount> {

    override fun unwrapDto(): Amount {
        return Amount(currency, minorUnits)
    }

}