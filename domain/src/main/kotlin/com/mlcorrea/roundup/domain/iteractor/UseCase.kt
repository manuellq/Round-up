package com.mlcorrea.roundup.domain.iteractor

import com.google.gdata.util.common.base.Preconditions
import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by manuel on 17/08/19
 *
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 *
 * By convention each UseCase implementation will return the result using a [ ]
 * that will execute its job in a background thread and will post the result in the UI thread.
 */

abstract class UseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor, private val postExecutionThread: PostExecutionThread
) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseObservable(params: Params): Observable<T>

    /**
     * Executes the current use case.
     *
     * @param observer [DisposableObserver] which will be listening to the observable build
     * by [.buildUseCaseObservable] ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(observer: DisposableObserver<T>, params: Params) {
        Preconditions.checkNotNull(observer)

        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(this.threadExecutor))
            .observeOn(this.postExecutionThread.getScheduler())

        addDisposable(observable.subscribeWith(observer))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!this.disposables.isDisposed) {
            this.disposables.dispose()
        }
    }

    fun clear() {
        if (!this.disposables.isDisposed) {
            this.disposables.clear()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(this.disposables)
        this.disposables.add(disposable)
    }

}