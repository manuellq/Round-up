package com.mlcorrea.roundup.framework.network

import android.content.Context
import com.mlcorrea.roundup.data.repository.PlatformRepositoryImpl
import com.mlcorrea.roundup.domain.repository.PlatformRepository
import com.mlcorrea.roundup.framework.retrofit.apimanager.ApiManager
import com.mlcorrea.roundup.framework.retrofit.repository.ApiControllerImpl

/**
 * Created by manuel on 17/08/19
 */
object Injection {

    fun providePlatformRepositoryImpl(
        context: Context,
        apiManager: ApiManager
    ): PlatformRepository {
        return PlatformRepositoryImpl(ApiControllerImpl(apiManager))
    }
}