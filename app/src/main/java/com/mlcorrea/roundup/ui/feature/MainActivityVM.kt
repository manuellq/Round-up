package com.mlcorrea.roundup.ui.feature

import androidx.lifecycle.ViewModel
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.framework.livedata.SingleLiveEvent
import java.math.BigDecimal

/**
 * Created by manuel on 17/08/19
 */
class MainActivityVM : ViewModel() {

    private var accounts: List<Accounts> = emptyList()
    private var accountSelected: Accounts? = null
    private var _goalStepEvent: SingleLiveEvent<GoalStep> = SingleLiveEvent()
    val goalStepEvent: SingleLiveEvent<GoalStep> = _goalStepEvent


    fun setAccountList(accounts: List<Accounts>) {
        this.accounts = accounts
    }

    fun getAccount(): Accounts? {
        return accountSelected
    }

    fun moveStep(goalStep: GoalStep) {
        when (goalStep) {
            is GoalStep.StepTransactions -> {
                accountSelected = goalStep.accounts
            }
        }
        goalStepEvent.value = goalStep
    }
}

sealed class GoalStep {
    class StepAccount(val successToast: Boolean) : GoalStep()
    class StepTransactions(val accounts: Accounts?) : GoalStep()
    class StepGoals(val totalSavings: BigDecimal) : GoalStep()
}
