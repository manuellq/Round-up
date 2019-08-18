package com.mlcorrea.roundup.ui.feature.roundup

import com.mlcorrea.roundup.domain.iteractor.accountpersonal.GetAccounts
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.network.NetworkRequestState
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

/**
 * Created by manuel on 17/08/19
 */
class RoundUpFragmentVM constructor(private val getAccounts: GetAccounts) : BaseSimpleListViewModel() {

    private var isViewModelLoaded = false

    override fun onCleared() {
        getAccounts.dispose()
        super.onCleared()
    }

    override fun getNetworkData() {
        getAccounts.execute(object : DisposableObserver<List<Accounts>>() {
            override fun onComplete() {
                Timber.d("on complete get accounts")
            }

            override fun onNext(result: List<Accounts>) {
                onSuccessResponse(result)
            }

            override fun onError(e: Throwable) {
                onErrorResponse(e)
                Timber.e(e)
            }
        }, Unit)
    }

    fun setViewModel() {
        if (isViewModelLoaded) return
        isViewModelLoaded = true
        setNetworkValue(NetworkRequestState.INIT)
        getNetworkData()
    }

}