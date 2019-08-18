package com.mlcorrea.roundup.ui.feature.roundup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mlcorrea.roundup.UnitTest
import com.mlcorrea.roundup.domain.iteractor.accountpersonal.GetAccounts
import com.mlcorrea.roundup.domain.network.NetworkRequestState
import com.mlcorrea.roundup.domain.network.StatusRequest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by manuel on 18/08/19
 */
class RoundUpFragmentVMTest : UnitTest() {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxUnitFun = true)
    lateinit var getAccounts: GetAccounts

    @MockK(relaxUnitFun = true)
    lateinit var networkState: Observer<NetworkRequestState>

    private lateinit var viewModel: RoundUpFragmentVM

    @Before
    fun setUp() {
        viewModel = RoundUpFragmentVM(getAccounts)
    }


    @Test
    fun getNetworkData() {
        //Given
        viewModel.getNetworkState().observeForever(networkState)
        //When
        viewModel.getNetworkData()
        //Then
        verify { getAccounts.execute(any(),any()) }
    }

}