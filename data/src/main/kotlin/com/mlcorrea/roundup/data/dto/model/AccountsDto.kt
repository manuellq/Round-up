package com.mlcorrea.roundup.data.dto.model

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.domain.model.Accounts
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class AccountsDto(
    @Json(name = "accountUid")
    val accountUid: String,
    @Json(name = "defaultCategory")
    val defaultCategory: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "createdAt")
    val createdAt: String
) : BaseDto<Accounts> {

    override fun unwrapDto(): Accounts {
        return Accounts(accountUid, defaultCategory, currency, createdAt)
    }

}