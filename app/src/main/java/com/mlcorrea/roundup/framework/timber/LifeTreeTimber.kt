package com.mlcorrea.roundup.framework.timber

import android.util.Log
import timber.log.Timber

/**
 * Created by manuel on 17/08/19
 */
class LifeTreeTimber : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, error: Throwable?) {
        //we will not log anything in release mode
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }
        //here we can add code to send to our analytics frameworks
    }
}