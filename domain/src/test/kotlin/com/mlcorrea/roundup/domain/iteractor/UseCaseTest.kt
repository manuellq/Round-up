package com.mlcorrea.roundup.domain.iteractor

import com.google.common.truth.Truth
import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by manuel on 18/08/19
 */
class UseCaseTest {


    private var useCase: UseCaseTestClass? = null

    private lateinit var testObserver: TestDisposableObserver<Any>

    @RelaxedMockK
    private lateinit var mockThreadExecutor: ThreadExecutor
    @RelaxedMockK
    private lateinit var mockPostExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.useCase = UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread)
        this.testObserver = TestDisposableObserver()

        every { mockPostExecutionThread.getScheduler() }.answers { TestScheduler() }
    }

    @Test
    fun `test build useCase observable return correct result`() {
        //given
        //when
        useCase?.execute(testObserver, MyParams.EMPTY_DATA)
        //then
        Assert.assertEquals(testObserver.valuesCount, 0)
    }

    @Test
    fun `test subscription when executing UseCase`() {
        //given
        //when
        useCase?.execute(testObserver, MyParams.EMPTY_DATA)
        useCase?.dispose()
        //then
        Assert.assertTrue(testObserver.isDisposed)
    }

    private inner class UseCaseTestClass constructor(
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) : UseCase<Any, MyParams>(threadExecutor, postExecutionThread) {


        override fun buildUseCaseObservable(params: MyParams): Observable<Any> {
            return Observable.empty()
        }

    }

    private class TestDisposableObserver<T> : DisposableObserver<T>() {
        var valuesCount = 0

        override fun onNext(value: T) {
            valuesCount++
        }

        override fun onError(e: Throwable) {
            // no-op by default.
        }

        override fun onComplete() {
            // no-op by default.
        }
    }

    data class MyParams(val name: String) {
        companion object {
            val EMPTY_DATA = MyParams("")
        }
    }
}