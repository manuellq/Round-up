package com.mlcorrea.roundup.domain.model

/**
 * Created by manuel on 17/08/19
 */
data class FeedItem(
    val feedItemUid: String,
    val categoryUid: String,
    val amount: Amount,
    val sourceAmount: Amount,
    val direction: String,
    val updatedAt: String,
    val transactionTime: String,
    val settlementTime: String?,
    val source: String,
    val sourceSubType: String?,
    val status: String?,
    val counterPartyType: String?,
    val counterPartyUid: String?,
    val counterPartyName: String?,
    val counterPartySubEntityUid: String?,
    val counterPartySubEntityName: String?,
    val counterPartySubEntityIdentifier: String?,
    val counterPartySubEntitySubIdentifier: String?,
    val reference: String?,
    val country: String?,
    val spendingCategory: String?,
    val userNote: String?
) : ViewModelData