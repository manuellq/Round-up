package com.mlcorrea.roundup.ui.feature.transaction.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.framework.extension.FORMAT_TIME_ZONE
import com.mlcorrea.roundup.framework.extension.getTime
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import java.util.*

/**
 * Created by manuel on 17/08/19
 */
class TransactionsDiffCallBack(adapter: RecyclerView.Adapter<*>) :
    SortedListComparatorWrapper<ViewModelData>(
        adapter,
        DEFAULT_ORDER
    ) {

    override fun areItemsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is FeedItem && newItem is FeedItem) {
            oldItem.feedItemUid == newItem.feedItemUid
        } else {
            throw IllegalArgumentException("Model must be FeedItem")
        }
    }

    override fun areContentsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is FeedItem && newItem is FeedItem) {
            oldItem.status == newItem.status && oldItem.amount.currency == newItem.amount.currency
                    && oldItem.updatedAt == newItem.updatedAt
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
        return if (oldItem is FeedItem && newItem is FeedItem) {
            newItem.transactionTime.getTime(FORMAT_TIME_ZONE)
                .compareTo(oldItem.transactionTime.getTime(FORMAT_TIME_ZONE))
        } else {
            throw IllegalArgumentException("Model must be a FeedItem")
        }
    }
}