package com.mlcorrea.roundup.framework.retrofit.helper

import com.mlcorrea.roundup.framework.retrofit.service.ApiService
import retrofit2.Retrofit

/**
 * Created by manuel on 17/08/19
 */
interface ApiServiceHelperController {

    fun createRetrofit(): Retrofit

    fun createService(retrofit: Retrofit): ApiService
}