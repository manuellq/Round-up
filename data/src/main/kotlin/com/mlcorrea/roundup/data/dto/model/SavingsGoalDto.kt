package com.mlcorrea.roundup.data.dto.model

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */

@JsonClass(generateAdapter = true)
data class SavingsGoalDto(
    @Json(name = "savingsGoalUid")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "savedPercentage")
    val savedPercentage: Int,
    @Json(name = "target")
    val targetDto: AmountDto,
    @Json(name = "totalSaved")
    val totalSavedDto: AmountDto
) : BaseDto<SavingsGoal> {

    override fun unwrapDto(): SavingsGoal {
        return SavingsGoal(id, name, savedPercentage, targetDto.unwrapDto(), totalSavedDto.unwrapDto())
    }

}