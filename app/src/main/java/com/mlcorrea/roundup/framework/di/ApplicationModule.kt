package com.mlcorrea.roundup.framework.di

import com.mlcorrea.roundup.BuildConfig
import com.mlcorrea.roundup.data.executor.JobExecutor
import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.accountpersonal.GetAccounts
import com.mlcorrea.roundup.domain.iteractor.goals.AddMoneySavingsGoal
import com.mlcorrea.roundup.domain.iteractor.goals.AddSavingGoal
import com.mlcorrea.roundup.domain.iteractor.goals.GetSavingsGoal
import com.mlcorrea.roundup.domain.iteractor.transactionfeed.TransactionRangeDate
import com.mlcorrea.roundup.framework.network.Injection
import com.mlcorrea.roundup.framework.uuid.UuidService
import com.mlcorrea.roundup.framework.uuid.UuidUser
import com.mlcorrea.roundup.ui.UIThread
import com.mlcorrea.roundup.ui.feature.MainActivity
import com.mlcorrea.roundup.ui.feature.MainActivityVM
import com.mlcorrea.roundup.ui.feature.goals.GoalsFragment
import com.mlcorrea.roundup.ui.feature.goals.GoalsFragmentVM
import com.mlcorrea.roundup.ui.feature.roundup.RoundUpFragment
import com.mlcorrea.roundup.ui.feature.roundup.RoundUpFragmentVM
import com.mlcorrea.roundup.ui.feature.transaction.TransactionsFragment
import com.mlcorrea.roundup.ui.feature.transaction.TransactionsFragmentVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by manuel on 17/08/19
 */

const val IS_DEBUG_QUALIFIER = "isDebug"

val appModule = module {

    single {
        Injection.providePlatformRepositoryImpl(get(), get())
    }

    factory<UuidService> { UuidUser() }
}

val dataModule = module {

    single<ThreadExecutor> { JobExecutor() }
    single<PostExecutionThread> { UIThread() }
    factory(named(IS_DEBUG_QUALIFIER)) { BuildConfig.DEBUG }

}

val fragmentScope = module {
    scope(named<RoundUpFragment>()) {
        viewModel { RoundUpFragmentVM(get()) }
        scoped { GetAccounts(get(), get(), get()) }
    }

    scope(named<TransactionsFragment>()) {
        viewModel { TransactionsFragmentVM(get(), get(), get()) }
        scoped { TransactionRangeDate(get(), get(), get()) }
    }

    scope(named<GoalsFragment>()) {
        viewModel { GoalsFragmentVM(get(), get(), get(), get()) }
        scoped { GetSavingsGoal(get(), get(), get()) }
        scoped { AddSavingGoal(get(), get(), get()) }
        scoped { AddMoneySavingsGoal(get(), get(), get()) }
    }
}

val activityScope = module {
    scope(named<MainActivity>()) {
        viewModel { MainActivityVM() }
    }
}