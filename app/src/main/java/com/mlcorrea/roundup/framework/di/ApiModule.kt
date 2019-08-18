package com.mlcorrea.roundup.framework.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mlcorrea.roundup.BuildConfig
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManager
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManagerImpl
import com.mlcorrea.roundup.framework.retrofit.helper.ApiServiceHelperController
import com.mlcorrea.roundup.framework.retrofit.helper.ApiServiceHelperControllerImpl
import com.mlcorrea.roundup.framework.util.UtilsSecurityNetwork
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by manuel on 17/08/19
 */

private const val HTTP_LOGIN_INTERCEPTOR_QUALIFIER = "httpLoggingInterceptor"
private const val STETHO_INTERCEPTOR_QUALIFIER = "stethoInterceptor"
private const val TOKEN_INTERCEPTOR_QUALIFIER = "tokenInterceptor"
private const val API_URL_QUALIFIER = "api_url"

val apiModule = module {

    single<ApiManager> { ApiManagerImpl(get()) }

    factory<ApiServiceHelperController> { ApiServiceHelperControllerImpl(get(named(API_URL_QUALIFIER)), get()) }

    single {
        UtilsSecurityNetwork
            .getOkHttpClient(
                get(named(STETHO_INTERCEPTOR_QUALIFIER)),
                get(named(HTTP_LOGIN_INTERCEPTOR_QUALIFIER)),
                get(named(TOKEN_INTERCEPTOR_QUALIFIER)),
                get(named(IS_DEBUG_QUALIFIER))
            )
    }

    factory(named(API_URL_QUALIFIER)) { BuildConfig.BASE_URL }


    single(named(HTTP_LOGIN_INTERCEPTOR_QUALIFIER)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (get(named(IS_DEBUG_QUALIFIER))) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpLoggingInterceptor
    }

    single(named(STETHO_INTERCEPTOR_QUALIFIER)) { StethoInterceptor() }

    factory(named(TOKEN_INTERCEPTOR_QUALIFIER)) {
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", BuildConfig.ACCESS_TOKEN)
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
    }
}