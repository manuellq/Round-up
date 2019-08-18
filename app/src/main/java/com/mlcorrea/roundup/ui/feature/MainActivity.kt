package com.mlcorrea.roundup.ui.feature

import android.os.Bundle
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.framework.extension.observe
import com.mlcorrea.roundup.ui.base.BaseActivity
import com.mlcorrea.roundup.ui.feature.goals.GoalsFragment
import com.mlcorrea.roundup.ui.feature.roundup.RoundUpFragment
import com.mlcorrea.roundup.ui.feature.transaction.TransactionsFragment
import org.jetbrains.anko.longToast
import org.koin.androidx.scope.currentScope
import java.math.BigDecimal

class MainActivity : BaseActivity() {

    private val viewModel by currentScope.inject<MainActivityVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(false)
        viewModel.apply {
            observe(goalStepEvent, ::handleStepEvent)
        }
        val visibleFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)
        if (visibleFragment == null) {
            addFragment(R.id.container_fragment, RoundUpFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.container_fragment)) {
            is RoundUpFragment -> super.onBackPressed()
            is TransactionsFragment -> openAccounts()
            is GoalsFragment -> {
                val accounts = viewModel.getAccount()
                if (accounts != null) {
                    openTransactionFragment(accounts)
                } else {
                    openAccounts()
                }
            }
        }
    }

    private fun handleStepEvent(goalStep: GoalStep?) {
        when (goalStep) {
            is GoalStep.StepTransactions -> openTransactionFragment(goalStep.accounts)
            is GoalStep.StepGoals -> openSavingsGoal(goalStep.totalSavings)
            is GoalStep.StepAccount -> {
                longToast(getString(R.string.text_savings_complete))
                openAccounts()
            }
        }
    }

    private fun openAccounts() {
        addFragment(R.id.container_fragment, RoundUpFragment.newInstance())
    }

    private fun openSavingsGoal(totalSavings: BigDecimal) {
        viewModel.getAccount()?.let {
            addFragment(R.id.container_fragment, GoalsFragment.newInstance(totalSavings, it))
        }
    }

    private fun openTransactionFragment(accounts: Accounts?) {
        accounts?.let {
            addFragment(R.id.container_fragment, TransactionsFragment.newInstance(accounts))
        }
    }

    fun getViewModelParent(): MainActivityVM? {
        return viewModel
    }
}
