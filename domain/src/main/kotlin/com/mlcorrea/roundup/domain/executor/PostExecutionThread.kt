package com.mlcorrea.roundup.domain.executor

import io.reactivex.Scheduler

/**
 * Created by manuel on 17/08/19
 *
 * Thread abstraction created to change the execution context from any thread to any other thread.
 * Useful to encapsulate a UI Thread for example, since some job will be done in background, an
 * implementation of this interface will change context and update the UI.
 */
interface PostExecutionThread {

    abstract fun getScheduler(): Scheduler
}