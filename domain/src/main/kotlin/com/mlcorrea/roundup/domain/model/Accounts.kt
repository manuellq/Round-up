package com.mlcorrea.roundup.domain.model

import java.io.Serializable

/**
 * Created by manuel on 17/08/19
 */
data class Accounts(
    val accountUid: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String
) : ViewModelData, Serializable