package com.mlcorrea.roundup.framework.retrofit.repository

import com.mlcorrea.roundup.UnitTest
import com.mlcorrea.roundup.data.dto.AccountsResponse
import com.mlcorrea.roundup.data.dto.CreateGoalResponse
import com.mlcorrea.roundup.data.dto.SavingsGoalResponse
import com.mlcorrea.roundup.data.dto.TransactionResponse
import com.mlcorrea.roundup.data.dto.input.CreateGoalBody
import com.mlcorrea.roundup.data.network.ApiController
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManager
import com.mlcorrea.roundup.framework.retrofit.service.ApiService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by manuel on 18/08/19
 */
class ApiControllerImplTest : UnitTest() {

    @MockK
    lateinit var apiManager: ApiManager
    @MockK
    lateinit var apiService: ApiService


    private lateinit var apiController: ApiController

    @Before
    fun setUp() {
        apiController = ApiControllerImpl(apiManager)
        every { apiManager.apiServices }.returns(apiService)
    }

    @Test
    fun `get accounts no error`() {
        //Given
        val testObserver = TestObserver<AccountsResponse>()
        val responseMock = mockk<AccountsResponse>()
        every { apiService.getAccounts() }.returns(Observable.just(responseMock))
        //When
        val result = apiController.getAccounts()
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertValue(responseMock)
    }

    @Test
    fun `get the feed item for a range of days`() {
        //Given
        val testObserver = TestObserver<TransactionResponse>()
        val responseMock = mockk<TransactionResponse>()
        every { apiService.getFeedItemsRangeDate(any(),any(),any(),any()) }.returns(Observable.just(responseMock))
        //When
        val result = apiController.getFeedItemsRangeDate("","","","")
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertValue(responseMock)
    }

    @Test
    fun `get savings goal`() {
        //Given
        val testObserver = TestObserver<SavingsGoalResponse>()
        val responseMock = mockk<SavingsGoalResponse>()
        every { apiService.getSavingsGoals(any()) }.returns(Observable.just(responseMock))
        //When
        val result = apiController.getSavingsGoals("")
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertValue(responseMock)
    }

    @Test
    fun `add a savings pot`() {
        //Given
        val testObserver = TestObserver<CreateGoalResponse>()
        val responseMock = mockk<CreateGoalResponse>()
        val body = mockk<CreateGoalBody>()
        every { apiService.addSavingPot(any(),any()) }.returns(Observable.just(responseMock))
        //When
        val result = apiController.addSavingGoal("",body)
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertValue(responseMock)
    }

}