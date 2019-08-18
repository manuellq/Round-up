package com.mlcorrea.roundup.ui.feature.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mlcorrea.roundup.data.extension.removeDecimalSeparator
import com.mlcorrea.roundup.domain.iteractor.goals.AddMoneySavingsGoal
import com.mlcorrea.roundup.domain.iteractor.goals.AddSavingGoal
import com.mlcorrea.roundup.domain.iteractor.goals.GetSavingsGoal
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.Amount
import com.mlcorrea.roundup.domain.model.ResponseRx
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.model.input.SavingGoalBody
import com.mlcorrea.roundup.domain.network.NetworkRequestState
import com.mlcorrea.roundup.framework.uuid.UuidService
import com.mlcorrea.roundup.ui.base.BaseSimpleListViewModel
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.math.BigDecimal
import java.text.NumberFormat

/**
 * Created by manuel on 18/08/19
 */
class GoalsFragmentVM constructor(
    private val getSavingsGoal: GetSavingsGoal,
    private val addSavingGoal: AddSavingGoal,
    private val addMoneySavingsGoal: AddMoneySavingsGoal,
    private val uuidService: UuidService
) : BaseSimpleListViewModel() {

    private val _requestProgress = MutableLiveData<ResponseRx<Boolean>>()
    val requestProgress: LiveData<ResponseRx<Boolean>> = _requestProgress
    private val _titleData = MutableLiveData<TitleSavingsGoal>()
    val titleData: LiveData<TitleSavingsGoal> = _titleData
    private var account: Accounts? = null
    private var isViewModelLoaded = false
    private var totalSaving: BigDecimal? = null

    override fun onCleared() {
        getSavingsGoal.dispose()
        addSavingGoal.dispose()
        addMoneySavingsGoal.dispose()
        super.onCleared()
    }

    override fun getNetworkData() {
        account?.let {
            getSavingsGoal.execute(object : DisposableObserver<List<SavingsGoal>>() {
                override fun onComplete() {
                    Timber.d("on Complete get goal")
                }

                override fun onNext(t: List<SavingsGoal>) {
                    Timber.d("on Next complete goal")
                    onSuccessResponse(t)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    onErrorResponse(e)
                }
            }, GetSavingsGoal.Params(it.accountUid))
        }
    }

    fun setViewModel(totalSaving: BigDecimal?, account: Accounts?) {
        if (isViewModelLoaded) return
        isViewModelLoaded = true
        this.totalSaving = totalSaving
        this.account = account
        setNetworkValue(NetworkRequestState.INIT)
        getNetworkData()

        if (totalSaving != null && account != null) {
            _titleData.value = TitleSavingsGoal(NumberFormat.getNumberInstance().format(totalSaving), account.currency)
        }
    }

    fun createSavingGoal(text: String) {
        _requestProgress.value = ResponseRx.Loading()
        account?.let {
            addSavingGoal.execute(
                object : DisposableObserver<String>() {
                    override fun onComplete() {
                        Timber.d("on Complete get goal")
                    }

                    override fun onNext(t: String) {
                        Timber.d("on Next saving goal")
                        _requestProgress.value = ResponseRx.Success(false)
                        getNetworkData()
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        _requestProgress.value = ResponseRx.Error(e as Exception)
                    }
                },
                AddSavingGoal.Params(
                    it.accountUid,
                    SavingGoalBody(text, it.currency, Amount(it.currency, "100000"), "")
                )
            )
        }
    }

    fun addMoneySavingsGoal(savingsGoal: SavingsGoal) {
        val savings = totalSaving ?: return
        account?.let {
            _requestProgress.value = ResponseRx.Loading()
            addMoneySavingsGoal.execute(
                object : DisposableObserver<String>() {
                    override fun onComplete() {
                        Timber.d("on Complete add savings goal")
                    }

                    override fun onNext(t: String) {
                        Timber.d("on Success add savings goal")
                        _requestProgress.value = ResponseRx.Success(true)
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        _requestProgress.value = ResponseRx.Error(e as Exception)
                    }
                },
                AddMoneySavingsGoal.Params(
                    it.accountUid,
                    savingsGoal.id,
                    uuidService.getUuid(),
                    Amount(it.currency, savings.removeDecimalSeparator())
                )
            )
        }
    }

}

data class TitleSavingsGoal(val amount: String, val currency: String)