package com.mlcorrea.roundup.ui.feature.goals.adpter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.data.extension.setDecimalPoint
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.ui.renders.ViewRenderer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

/**
 * Created by manuel on 18/08/19
 */
class SavingsViewRender(private val clickListener: (View, SavingsGoal) -> Unit = { _: View, _: SavingsGoal -> }) :
    ViewRenderer<SavingsGoal, SavingsViewModel>(SavingsGoal::class.java) {

    @SuppressLint("CheckResult")
    override fun bindView(model: SavingsGoal, holder: SavingsViewModel) {
        holder.apply {
            val amount = model.totalSavedDto.minorUnits.toBigDecimalOrNull()?.setDecimalPoint()
            val targetAmount = model.targetDto.minorUnits.toBigDecimalOrNull()?.setDecimalPoint()
            uiName.text = model.name
            uiAmount.text = NumberFormat.getNumberInstance().format(amount)
            uiCurrency.text = model.totalSavedDto.currency
            uiTargetAmount.text = NumberFormat.getNumberInstance().format(targetAmount)
        }

        RxView.clicks(holder.itemView)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                clickListener(holder.itemView, model)
            }
    }

    override fun createViewHolder(parent: ViewGroup): SavingsViewModel {
        return SavingsViewModel(inflate(R.layout.layout_row_savings, parent))
    }
}