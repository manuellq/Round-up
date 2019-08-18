package com.mlcorrea.roundup.data.dto.base

/**
 * Created by manuel on 17/08/19
 */
interface BaseDto<T> {

    fun unwrapDto(): T
}