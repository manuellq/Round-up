package com.mlcorrea.roundup.domain.model

/**
 * Created by manuel on 17/08/19
 */
sealed class ResponseRx<T> {
    class Loading<T> : ResponseRx<T>()
    class Error<T>(val exception: Exception) : ResponseRx<T>()
    class Success<T>(val data: T? = null) : ResponseRx<T>()
}