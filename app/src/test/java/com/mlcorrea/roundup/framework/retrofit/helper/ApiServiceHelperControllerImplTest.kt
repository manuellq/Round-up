package com.mlcorrea.roundup.framework.retrofit.helper

import com.google.common.truth.Truth
import com.mlcorrea.roundup.UnitTest
import com.mlcorrea.roundup.framework.retrofit.service.ApiService
import io.mockk.impl.annotations.MockK
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

/**
 * Created by manuel on 18/08/19
 */
class ApiServiceHelperControllerImplTest : UnitTest() {

    @MockK
    lateinit var httpClient: OkHttpClient

    private lateinit var apiServiceHelperController: ApiServiceHelperController

    @Before
    fun setUp() {
        apiServiceHelperController = ApiServiceHelperControllerImpl("http://ws.audioscrobbler.com/", httpClient)
    }

    @Test
    fun createRetrofit() {
        Truth.assertThat(apiServiceHelperController.createRetrofit()).isInstanceOf(Retrofit::class.java)
    }

    @Test
    fun createService() {
        val retrofit = apiServiceHelperController.createRetrofit()
        Truth.assertThat(apiServiceHelperController.createService(retrofit)).isInstanceOf(ApiService::class.java)
    }
}