package com.mlcorrea.roundup.ui.feature.roundup.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import java.util.*

/**
 * Created by manuel on 17/08/19
 */
class AccountDiffCallBack(adapter: RecyclerView.Adapter<*>) :
    SortedListComparatorWrapper<ViewModelData>(
        adapter,
        DEFAULT_ORDER
    ) {

    override fun areItemsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is Accounts && newItem is Accounts) {
            oldItem.accountUid == newItem.accountUid
        } else {
            throw IllegalArgumentException("Model must be Accounts")
        }
    }

    override fun areContentsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is Accounts && newItem is Accounts) {
            oldItem.accountUid == newItem.accountUid
        } else {
            false
        }
    }

    companion object {
        private val DEFAULT_ORDER = OrderByName()
    }

}

class OrderByName : Comparator<ViewModelData> {
    override fun compare(oldItem: ViewModelData, newItem: ViewModelData): Int {
        return if (oldItem is Accounts && newItem is Accounts) {
            newItem.currency.compareTo(oldItem.currency)
        } else {
            throw IllegalArgumentException("Model must be a Account")
        }
    }
}