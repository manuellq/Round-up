package com.mlcorrea.roundup.ui.base

import androidx.lifecycle.MutableLiveData
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.domain.network.NetworkRequestState
import timber.log.Timber

/**
 * Created by manuel.correa on 17/08/19
 */
abstract class BaseSimpleListViewModel : BaseListViewModel() {

    private val responseFromNetwork: MutableLiveData<List<ViewModelData>> = MutableLiveData()

    fun handleResponseList(): MutableLiveData<List<ViewModelData>> {
        return responseFromNetwork
    }

    fun getListNetwork(): List<ViewModelData>? {
        return responseFromNetwork.value
    }

    fun setListNetwork(list: List<ViewModelData>?) {
        responseFromNetwork.value = list
    }

    fun refresh() {
        Timber.d(getNetWorkValue()?.toString())
        if ((getNetWorkValue() == NetworkRequestState.LOADED || getNetWorkValue() == NetworkRequestState.EMPTY)) {
            setProgressbarRefresh(true)
            getNetworkData()
        } else {
            stopProgressBareRefresh()
        }
    }

    fun onSuccessResponse(viewModelList: List<ViewModelData>) {
        Timber.d("on Success getting list")
        setProgressbarRefresh(false)
        if (viewModelList.isEmpty()) {
            setNetworkValue(NetworkRequestState.EMPTY)
            responseFromNetwork.value = emptyList()
        } else {
            setNetworkValue(NetworkRequestState.LOADED)
            responseFromNetwork.value = viewModelList
        }
    }

    fun onErrorResponse(e: Throwable) {
        Timber.d("on Error getting list")
        setProgressbarRefresh(false)
        setNetworkValue(NetworkRequestState.error(e))
    }
}