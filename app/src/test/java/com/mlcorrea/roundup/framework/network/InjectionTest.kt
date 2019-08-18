package com.mlcorrea.roundup.framework.network

import android.content.Context
import com.google.common.truth.Truth
import com.mlcorrea.roundup.UnitTest
import com.mlcorrea.roundup.data.repository.PlatformRepositoryImpl
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManager
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

/**
 * Created by manuel on 18/08/19
 */
class InjectionTest : UnitTest() {


    @Before
    fun setUp() {
    }

    @Test
    fun providePlatformRepositoryImpl() {
        val apiManager = mockk<ApiManager>()
        val context = mockk<Context>()
        val result = Injection.providePlatformRepositoryImpl(context, apiManager)

        Truth.assertThat(result).isInstanceOf(PlatformRepositoryImpl::class.java)
    }
}