package com.mlcorrea.roundup.data.dto.model

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.domain.model.FeedItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class FeedItemDto(
    @Json(name = "feedItemUid")
    val feedItemUid: String,
    @Json(name = "categoryUid")
    val categoryUid: String,
    @Json(name = "amount")
    val amount: AmountDto,
    @Json(name = "sourceAmount")
    val sourceAmount: AmountDto,
    @Json(name = "direction")
    val direction: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "transactionTime")
    val transactionTime: String?,
    @Json(name = "settlementTime")
    val settlementTime: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "sourceSubType")
    val sourceSubType: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "counterPartyType")
    val counterPartyType: String?,
    @Json(name = "counterPartyUid")
    val counterPartyUid: String?,
    @Json(name = "counterPartyName")
    val counterPartyName: String?,
    @Json(name = "counterPartySubEntityUid")
    val counterPartySubEntityUid: String?,
    @Json(name = "counterPartySubEntityName")
    val counterPartySubEntityName: String?,
    @Json(name = "counterPartySubEntityIdentifier")
    val counterPartySubEntityIdentifier: String?,
    @Json(name = "counterPartySubEntitySubIdentifier")
    val counterPartySubEntitySubIdentifier: String?,
    @Json(name = "reference")
    val reference: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "spendingCategory")
    val spendingCategory: String?,
    @Json(name = "userNote")
    val userNote: String?
) : BaseDto<FeedItem> {

    override fun unwrapDto(): FeedItem {
        return FeedItem(
            feedItemUid,
            categoryUid,
            amount.unwrapDto(),
            sourceAmount.unwrapDto(),
            direction,
            updatedAt,
            transactionTime ?: "",
            settlementTime,
            source,
            sourceSubType,
            status,
            counterPartyType,
            counterPartyUid,
            counterPartyName,
            counterPartySubEntityUid,
            counterPartySubEntityName,
            counterPartySubEntityIdentifier,
            counterPartySubEntitySubIdentifier,
            reference,
            country,
            spendingCategory,
            userNote
        )
    }

}