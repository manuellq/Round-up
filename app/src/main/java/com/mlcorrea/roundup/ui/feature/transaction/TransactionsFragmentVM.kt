package com.mlcorrea.roundup.ui.feature.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mlcorrea.roundup.data.extension.roundUpFeedItem
import com.mlcorrea.roundup.domain.executor.PostExecutionThread
import com.mlcorrea.roundup.domain.executor.ThreadExecutor
import com.mlcorrea.roundup.domain.iteractor.transactionfeed.TransactionRangeDate
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.ResponseRx
import com.mlcorrea.roundup.framework.extension.FORMAT_TIME_BACKEND
import com.mlcorrea.roundup.framework.extension.getDateMilliseconds
import com.mlcorrea.roundup.framework.extension.parseToDateSting
import com.mlcorrea.roundup.framework.extension.setDateDays
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * Created by manuel on 17/08/19
 */
class TransactionsFragmentVM constructor(
    private val transactionRangeDate: TransactionRangeDate,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) :
    BaseSimpleListViewModel() {

    private val _rangeDateFeed = MutableLiveData<RangeDate>()
    val rangeDateFeed: LiveData<RangeDate> = _rangeDateFeed
    private val _roundUpFeedItems = MutableLiveData<ResponseRx<BigDecimal>>()
    val roundUpFeedItems: LiveData<ResponseRx<BigDecimal>> = _roundUpFeedItems

    private var isViewModelLoaded = false
    private var account: Accounts? = null
    private val disposable = CompositeDisposable()


    override fun onCleared() {
        transactionRangeDate.dispose()
        disposable.dispose()
        super.onCleared()
    }

    override fun getNetworkData() {
        account?.let { accountLet ->
            val date = rangeDateFeed.value
            date?.let {
                transactionRangeDate.execute(
                    object : DisposableObserver<List<FeedItem>>() {
                        override fun onComplete() {
                            Timber.d("on complete range of dates")
                        }

                        override fun onNext(t: List<FeedItem>) {
                            Timber.d("on success date of range")
                            onSuccessResponse(t)
                        }

                        override fun onError(e: Throwable) {
                            Timber.e(e)
                            onErrorResponse(e)
                        }
                    },
                    TransactionRangeDate.Params(
                        accountLet.accountUid,
                        accountLet.defaultCategory,
                        it.startDate.parseToDateSting(FORMAT_TIME_BACKEND),
                        it.endDate.parseToDateSting(FORMAT_TIME_BACKEND)
                    )
                )
            }
        }
    }

    fun setViewModel(account: Accounts?) {
        if (isViewModelLoaded) return
        isViewModelLoaded = true
        this.account = account
    }

    fun setStartDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val startDate = getDateMilliseconds(year, monthOfYear, dayOfMonth)
        val endDate = startDate.setDateDays(ONE_WEEK)
        _rangeDateFeed.value = RangeDate(startDate, endDate)
        getNetworkData()
    }

    fun getRoundUp() {
        val response = getListNetwork()
        response?.let {
            _roundUpFeedItems.value = ResponseRx.Loading()
            disposable += response.roundUpFeedItem()
                .subscribeOn(Schedulers.from(this.threadExecutor))
                .observeOn(this.postExecutionThread.getScheduler())
                .subscribe({ total ->
                    Timber.d("Total sum: $total")
                    _roundUpFeedItems.value = ResponseRx.Success(total)
                }, { error ->
                    Timber.e(error)
                    _roundUpFeedItems.value = ResponseRx.Error(error as Exception)
                })
        }
    }

    companion object {
        private const val ONE_WEEK = 6
    }
}

data class RangeDate(val startDate: Long, val endDate: Long)