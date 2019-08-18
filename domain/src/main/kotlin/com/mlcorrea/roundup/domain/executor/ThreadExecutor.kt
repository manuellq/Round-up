package com.mlcorrea.roundup.domain.executor

import java.util.concurrent.Executor

/**
 * Created by manuel on 17/08/19
 *
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * out of the UI thread.
 */
interface ThreadExecutor : Executor