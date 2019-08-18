package com.mlcorrea.roundup.domain.network

import com.mlcorrea.roundup.domain.model.ViewModelData


/**
 * Created by manuel on 17/08/19
 */
enum class StatusRequest {
    INIT,
    RUNNING,
    SUCCESS,
    FAILED,
    EMPTY
}

data class NetworkRequestState(
    val status: StatusRequest,
    val exception: Throwable? = null
) : ViewModelData {

    companion object {
        val INIT = NetworkRequestState(StatusRequest.INIT)
        val LOADED = NetworkRequestState(StatusRequest.SUCCESS)
        val LOADING = NetworkRequestState(StatusRequest.RUNNING)
        val EMPTY = NetworkRequestState(StatusRequest.EMPTY)
        fun emptyError(exception: Throwable) = NetworkRequestState(StatusRequest.EMPTY, exception)
        fun error(exception: Throwable?) = NetworkRequestState(StatusRequest.FAILED, exception)
    }
}