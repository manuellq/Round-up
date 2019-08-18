package com.mlcorrea.roundup.domain.model

/**
 * Created by manuel on 18/08/19
 */
data class SavingsGoal(
    val id: String,
    val name: String,
    val savedPercentage: Int,
    val targetDto: Amount,
    val totalSavedDto: Amount
): ViewModelData