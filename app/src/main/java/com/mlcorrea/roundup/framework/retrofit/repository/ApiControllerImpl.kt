package com.mlcorrea.roundup.framework.retrofit.repository

import com.mlcorrea.roundup.data.dto.*
import com.mlcorrea.roundup.data.dto.input.AddMoneySavingsBody
import com.mlcorrea.roundup.data.dto.input.CreateGoalBody
import com.mlcorrea.roundup.data.dto.model.AmountDto
import com.mlcorrea.roundup.data.network.ApiController
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManager
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
class ApiControllerImpl constructor(private val apiManager: ApiManager) : ApiController {

    override fun getAccounts(): Observable<AccountsResponse> {
        return apiManager.apiServices.getAccounts()
    }

    override fun getFeedItemsRangeDate(
        accountUid: String,
        categoryUid: String,
        minDate: String,
        maxDate: String
    ): Observable<TransactionResponse> {
        return apiManager.apiServices.getFeedItemsRangeDate(accountUid, categoryUid, minDate, maxDate)
    }

    override fun getSavingsGoals(accountUid: String): Observable<SavingsGoalResponse> {
        return apiManager.apiServices.getSavingsGoals(accountUid)
    }

    override fun addSavingGoal(accountUid: String, body: CreateGoalBody): Observable<CreateGoalResponse> {
        return apiManager.apiServices.addSavingPot(accountUid, body)
    }

    override fun addMoneyToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        body: AddMoneySavingsBody
    ): Observable<AddMoneyGoalResponse> {
        return apiManager.apiServices.addMoneyToSavingsGoal(accountUid, savingsGoalUid, transferUid, body)
    }
}