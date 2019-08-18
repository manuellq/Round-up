package com.mlcorrea.roundup.data.dto

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.data.dto.model.FeedItemDto
import com.mlcorrea.roundup.domain.model.FeedItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class TransactionResponse(
    @Json(name = "feedItems")
    val feedItemDto: List<FeedItemDto>?,
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "errors")
    val errors: List<String>?
) : BaseDto<List<FeedItem>> {

    override fun unwrapDto(): List<FeedItem> {
        if (feedItemDto == null) return emptyList()

        val feedItemList = mutableListOf<FeedItem>()
        for (items in feedItemDto) {
            feedItemList.add(items.unwrapDto())
        }

        return feedItemList
    }

}