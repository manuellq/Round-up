package com.mlcorrea.roundup.framework.retrofit.service

import com.mlcorrea.roundup.data.dto.*
import com.mlcorrea.roundup.data.dto.input.AddMoneySavingsBody
import com.mlcorrea.roundup.data.dto.input.CreateGoalBody
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by manuel on 17/08/19
 */
interface ApiService {

    @GET("/api/v2/accounts")
    fun getAccounts(): Observable<AccountsResponse>

    @GET("/api/v2/feed/account/{account_uid}/category/{category_uid}/transactions-between")
    fun getFeedItemsRangeDate(
        @Path("account_uid") accountUid: String,
        @Path("category_uid") categoryUid: String,
        @Query("minTransactionTimestamp") minDate: String,
        @Query("maxTransactionTimestamp") maxDate: String
    ): Observable<TransactionResponse>

    @GET("/api/v2/account/{account_uid}/savings-goals")
    fun getSavingsGoals(@Path("account_uid") accountUid: String): Observable<SavingsGoalResponse>


    @PUT("/api/v2/account/{account_uid}/savings-goals")
    fun addSavingPot(@Path("account_uid") accountUid: String, @Body body: CreateGoalBody): Observable<CreateGoalResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goals/{savingsGoalUid}/add-money/{transfer_uid}")
    fun addMoneyToSavingsGoal(
        @Path("account_uid") accountUid: String, @Path("savingsGoalUid") savingsGoalUid: String, @Path("transfer_uid") transferUid: String,
        @Body body: AddMoneySavingsBody
    ): Observable<AddMoneyGoalResponse>
}