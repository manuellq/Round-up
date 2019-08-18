package com.mlcorrea.roundup.data.dto

import com.mlcorrea.roundup.data.dto.base.BaseDto
import com.mlcorrea.roundup.data.dto.model.SavingsGoalDto
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by manuel on 17/08/19
 */
@JsonClass(generateAdapter = true)
data class SavingsGoalResponse(
    @Json(name = "savingsGoalList")
    val savingsGoalDto: List<SavingsGoalDto>?,
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "errors")
    val errors: List<String>?
) : BaseDto<List<SavingsGoal>> {

    override fun unwrapDto(): List<SavingsGoal> {
        if (savingsGoalDto == null) return emptyList()

        val savingsGoalList = mutableListOf<SavingsGoal>()
        for (items in savingsGoalDto) {
            savingsGoalList.add(items.unwrapDto())
        }

        return savingsGoalList
    }

}