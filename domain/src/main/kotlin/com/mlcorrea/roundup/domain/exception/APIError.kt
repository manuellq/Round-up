package com.mlcorrea.roundup.domain.exception

/**
 * Created by manuel on 17/08/19
 */
data class APIError(val error: Int?, val messageError: String?, val success: Boolean = false) :
    RuntimeException(messageError)