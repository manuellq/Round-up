package com.mlcorrea.roundup.domain.model.input

import com.mlcorrea.roundup.domain.model.Amount

/**
 * Created by manuel on 18/08/19
 */
data class SavingGoalBody(
    val name: String,
    val currency: String,
    val target: Amount,
    val base64EncodedPhoto: String
)