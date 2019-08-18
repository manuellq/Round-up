package com.mlcorrea.roundup.ui

import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by manuel on 17/08/19
 */
class UIThread : PostExecutionThread {

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}