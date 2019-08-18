package com.mlcorrea.roundup.ui.error

import android.content.Context
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.exception.APIError

/**
 * Created by manuel on 17/08/19
 */
interface ErrorMessageFactory {

    companion object Factory {

        fun createFromException(context: Context, exception: Exception?): String {
            return when (exception) {
                is APIError -> {
                    exception.messageError ?: context.getString(R.string.exception_generic_message)
                }
                else -> context.getString(R.string.exception_generic_message)
            }
        }
    }
}

fun ErrorMessageFactory.Factory.fromException(context: Context, exception: Exception?) =
    this.createFromException(context, exception)