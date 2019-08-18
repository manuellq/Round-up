package com.mlcorrea.roundup.data.repository

import com.mlcorrea.roundup.data.dto.*
import com.mlcorrea.roundup.data.dto.input.AddMoneySavingsBody
import com.mlcorrea.roundup.data.dto.input.CreateGoalBody
import com.mlcorrea.roundup.data.dto.model.AmountDto
import com.mlcorrea.roundup.data.network.ApiController
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.Amount
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.model.input.SavingGoalBody
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import io.reactivex.Observable

/**
 * Created by manuel on 17/08/19
 */
class PlatformRepositoryImpl constructor(
    private val apiController: ApiController
) : PlatformRepository {

    override fun getAccounts(): Observable<List<Accounts>> {
        return apiController.getAccounts()
            .map { response: AccountsResponse ->
                response.unwrapDto()
            }
    }

    override fun getFeedItemsRangeDate(
        accountUid: String,
        categoryUid: String,
        minDate: String,
        maxDate: String
    ): Observable<List<FeedItem>> {
        return apiController.getFeedItemsRangeDate(accountUid, categoryUid, minDate, maxDate)
            .map { response: TransactionResponse ->
                response.unwrapDto()
            }
    }

    override fun getSavingsGoals(accountUid: String): Observable<List<SavingsGoal>> {
        return apiController.getSavingsGoals(accountUid)
            .map { response: SavingsGoalResponse ->
                response.unwrapDto()
            }
    }

    override fun addSavingGoal(accountUid: String, body: SavingGoalBody): Observable<String> {
        return Observable.defer {
            body.let {
                val bodyGoal = CreateGoalBody(
                    it.name,
                    it.currency,
                    AmountDto(it.target.currency, it.target.minorUnits),
                    it.base64EncodedPhoto
                )
                apiController.addSavingGoal(accountUid, bodyGoal)
            }
        }
            .map { response: CreateGoalResponse ->
                response.savingsGoalUid
            }
    }

    override fun addMoneyToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        body: Amount
    ): Observable<String> {
        return Observable.defer {
            val amount = AmountDto(body.currency, body.minorUnits)
            apiController.addMoneyToSavingsGoal(accountUid, savingsGoalUid, transferUid, AddMoneySavingsBody(amount))
        }
            .map { response: AddMoneyGoalResponse ->
                response.transferUid
            }
    }
}