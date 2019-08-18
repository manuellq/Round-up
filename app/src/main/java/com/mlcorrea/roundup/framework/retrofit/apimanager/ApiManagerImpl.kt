package com.mlcorrea.roundup.framework.retrofit.apimanager

import com.mlcorrea.roundup.framework.retrofit.helper.ApiServiceHelperController
import com.mlcorrea.roundup.framework.retrofit.service.ApiService
import retrofit2.Retrofit

/**
 * Created by manuel on 17/08/19
 */
class ApiManagerImpl constructor(
    private val apiServiceHelperController: ApiServiceHelperController
) : ApiManager {

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService

    init {
        createService()
    }

    override val apiServices: ApiService
        get() = apiService

    /*--------------PRIVATE METHOD------------*/

    private fun createService() {
        retrofit = apiServiceHelperController.createRetrofit()

        apiService = apiServiceHelperController.createService(retrofit)
    }
}