package com.mlcorrea.roundup.ui.feature.roundup.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.framework.extension.FORMAT_TIME_ZONE
import com.mlcorrea.roundup.framework.extension.getTime
import com.mlcorrea.roundup.framework.extension.parseToDateSting
import com.mlcorrea.roundup.ui.renders.ViewRenderer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by manuel on 17/08/19
 */
class AccountsViewRender(private val clickListener: (View, Accounts) -> Unit = { _: View, _: Accounts -> }) :
    ViewRenderer<Accounts, AccountsViewModel>(Accounts::class.java) {

    @SuppressLint("CheckResult")
    override fun bindView(model: Accounts, holder: AccountsViewModel) {
        holder.apply {
            uiCurrency.text = model.currency
            uiDate.text = model.createdAt.getTime(FORMAT_TIME_ZONE).parseToDateSting()
        }

        RxView.clicks(holder.itemView)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                clickListener(holder.itemView, model)
            }
    }

    override fun createViewHolder(parent: ViewGroup): AccountsViewModel {
        return AccountsViewModel(inflate(R.layout.layout_row_account, parent))
    }
}