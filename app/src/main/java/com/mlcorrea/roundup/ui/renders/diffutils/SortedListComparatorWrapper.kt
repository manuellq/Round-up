package com.mlcorrea.roundup.ui.renders.diffutils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback

/**
 * Created by manuel.correa on 22/03/2018.
 */
abstract class SortedListComparatorWrapper<T>(
  adapter: RecyclerView.Adapter<*>,
  private var comparator: Comparator<T>?
) :
  SortedListAdapterCallback<T>(adapter) {

    fun setComparator(comparator: Comparator<T>) {
        if (comparator == this.comparator) {
            return
        }

        this.comparator = comparator
    }

    override fun compare(o1: T, o2: T) = comparator!!.compare(o1, o2)
}