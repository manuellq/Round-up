package com.mlcorrea.roundup.domain.repository

import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.Amount
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.model.input.SavingGoalBody
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
interface PlatformRepository {

    fun getAccounts(): Observable<List<Accounts>>

    fun getFeedItemsRangeDate(
        accountUid: String,
        categoryUid: String,
        minDate: String,
        maxDate: String
    ): Observable<List<FeedItem>>

    fun getSavingsGoals(accountUid: String): Observable<List<SavingsGoal>>

    fun addSavingGoal(accountUid: String, body: SavingGoalBody): Observable<String>

    fun addMoneyToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        body: Amount
    ): Observable<String>
}