package com.mlcorrea.roundup.ui.feature.goals.adpter

import androidx.recyclerview.widget.RecyclerView
import com.mlcorrea.roundup.domain.model.SavingsGoal
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import java.util.*

/**
 * Created by manuel on 18/08/19
 */
class SavingsDiffCallBack(adapter: RecyclerView.Adapter<*>) :
    SortedListComparatorWrapper<ViewModelData>(
        adapter,
        DEFAULT_ORDER
    ) {

    override fun areItemsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is SavingsGoal && newItem is SavingsGoal) {
            oldItem.id == newItem.id
        } else {
            throw IllegalArgumentException("Model must be Accounts")
        }
    }

    override fun areContentsTheSame(oldItem: ViewModelData?, newItem: ViewModelData?): Boolean {
        return if (oldItem is SavingsGoal && newItem is SavingsGoal) {
            oldItem.savedPercentage == newItem.savedPercentage && oldItem.totalSavedDto.minorUnits == newItem.totalSavedDto.minorUnits
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
        return if (oldItem is SavingsGoal && newItem is SavingsGoal) {
            newItem.name.compareTo(oldItem.name)
        } else {
            throw IllegalArgumentException("Model must be a Account")
        }
    }
}