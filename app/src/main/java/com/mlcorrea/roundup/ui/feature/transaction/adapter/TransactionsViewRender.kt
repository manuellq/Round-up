package com.mlcorrea.roundup.ui.feature.transaction.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.data.extension.setDecimalPoint
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.ui.renders.ViewRenderer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

/**
 * Created by manuel on 17/08/19
 */
class TransactionsViewRender(private val clickListener: (View, FeedItem) -> Unit = { _: View, _: FeedItem -> }) :
    ViewRenderer<FeedItem, TransactionsViewHolder>(FeedItem::class.java) {

    @SuppressLint("CheckResult")
    override fun bindView(model: FeedItem, holder: TransactionsViewHolder) {
        holder.apply {
            val bigDecimal = model.amount.minorUnits.toBigDecimalOrNull()?.setDecimalPoint()
            uiCurrency.text = model.amount.currency
            uiAmount.text = NumberFormat.getNumberInstance().format(bigDecimal)
            uiType.text = model.counterPartyType
            uiReference.text = model.reference
        }

        RxView.clicks(holder.itemView)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                clickListener(holder.itemView, model)
            }
    }

    override fun createViewHolder(parent: ViewGroup): TransactionsViewHolder {
        return TransactionsViewHolder(inflate(R.layout.layout_row_transaction, parent))
    }
}