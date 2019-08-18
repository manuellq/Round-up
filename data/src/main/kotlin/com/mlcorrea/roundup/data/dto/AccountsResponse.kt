package com.mlcorrea.roundup.data.dto

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.data.dto.model.AccountsDto
import com.mlcorrea.roundup.domain.model.Accounts
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class AccountsResponse(
    @Json(name = "accounts")
    val accounts: List<AccountsDto>?
) : BaseDto<List<Accounts>> {

    override fun unwrapDto(): List<Accounts> {
        if (accounts == null) return emptyList()

        val accountList = mutableListOf<Accounts>()
        for (items in accounts) {
            accountList.add(items.unwrapDto())
        }

        return accountList
    }

}