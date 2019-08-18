package com.mlcorrea.roundup.data.network

import com.mlcorrea.roundup.data.dto.*
import com.mlcorrea.roundup.data.dto.input.AddMoneySavingsBody
import com.mlcorrea.roundup.data.dto.input.CreateGoalBody
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
interface ApiController {

    fun getAccounts(): Observable<AccountsResponse>

    fun getFeedItemsRangeDate(
        accountUid: String,
        categoryUid: String,
        minDate: String,
        maxDate: String
    ): Observable<TransactionResponse>

    fun getSavingsGoals(accountUid: String): Observable<SavingsGoalResponse>

    fun addSavingGoal(accountUid: String, body: CreateGoalBody): Observable<CreateGoalResponse>

    fun addMoneyToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        body: AddMoneySavingsBody
    ): Observable<AddMoneyGoalResponse>
}