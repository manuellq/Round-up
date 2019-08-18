package com.mlcorrea.roundup.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlcorrea.roundup.domain.network.NetworkRequestState

abstract class BaseListViewModel : ViewModel() {

    abstract fun getNetworkData()

    private val networkState = MutableLiveData<NetworkRequestState>()
    private val progressBarRefresh: MutableLiveData<Boolean> = MutableLiveData()

    fun getNetworkState(): MutableLiveData<NetworkRequestState> {
        return networkState
    }

    fun getProgressBarRefreshLive(): MutableLiveData<Boolean> {
        return progressBarRefresh
    }

    fun stopProgressBareRefresh() {
        progressBarRefresh.value = false
    }

    fun getProgressBareRefresh(): Boolean? {
        return progressBarRefresh.value
    }

    fun setNetworkValue(networkRequestState: NetworkRequestState) {
        networkState.value = networkRequestState
    }

    fun getNetWorkValue(): NetworkRequestState? {
        return networkState.value
    }

    fun setProgressbarRefresh(state: Boolean) {
        progressBarRefresh.value = state
    }

    fun retry() {
        if (getNetWorkValue() == NetworkRequestState.LOADING) {
            return
        }
        setNetworkValue(NetworkRequestState.LOADING)
        getNetworkData()
    }
}