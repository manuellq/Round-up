package com.mlcorrea.roundup.ui.renders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.domain.network.NetworkRequestState
import com.mlcorrea.roundup.ui.renders.base.*
import com.mlcorrea.roundup.ui.renders.baserenders.EmptyListViewRender
import com.mlcorrea.roundup.ui.renders.baserenders.LoadMoreViewRender
import com.mlcorrea.roundup.ui.renders.baserenders.ShimmerViewRender
import com.mlcorrea.roundup.ui.renders.diffutils.SortedListComparatorWrapper
import com.mlcorrea.roundup.ui.renders.model.EmptyListModel
import com.mlcorrea.roundup.ui.renders.model.ShimmerModal
import java.lang.reflect.Type
import java.util.*

/**
 * Created by manuel.correa on 19/03/2018.
 */

class RendererRecyclerViewSortedAdapter(
    private val loadMoreViewRender: DefaultLoadMoreViewRender<*> = LoadMoreViewRender(null),
    private val shimmerViewRender: DefaultShimmerViewRender = ShimmerViewRender(),
    private val emptyViewRender: DefaultEmptyStateViewRender = EmptyListViewRender()
) :
    RecyclerView.Adapter<ViewHolderA>() {

    private lateinit var itemSortedList: SortedList<ViewModelData>
    private val mRenderers = ArrayList<ViewRenderer<*, *>>()
    private val mTypes = ArrayList<Type>()
    private var networkState: NetworkRequestState? = null
    private lateinit var sortedListComparatorWrapper: SortedListComparatorWrapper<ViewModelData>

    init {
        registerRenderer(emptyViewRender)
        registerRenderer(loadMoreViewRender)
        registerRenderer(shimmerViewRender)
    }

    fun setComparatorWrapper(sortedListComparator: SortedListComparatorWrapper<ViewModelData>) {
        sortedListComparatorWrapper = sortedListComparator
        itemSortedList = SortedList(
            ViewModelData::class.java,
            SortedList.BatchedCallback<ViewModelData>(sortedListComparator)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderA {
        val renderer = mRenderers[viewType]
        if (renderer != null) {
            return renderer.performCreateViewHolder(parent)
        }
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    override fun onBindViewHolder(holder: ViewHolderA, position: Int) {
        if (getItemViewType(position) == getTypeIndex(loadMoreViewRender.getType())) {
            (loadMoreViewRender as DefaultLoadMoreViewRender<ViewHolderA>)
                .performBindView(networkState ?: NetworkRequestState.LOADING, holder)
        } else if (getItemViewType(position) == getTypeIndex(shimmerViewRender.getType())) {
            shimmerViewRender.performBindView(
                ShimmerModal(), holder as DefaultShimmerViewHolder
            )
        } else if (getItemViewType(position) == getTypeIndex(emptyViewRender.getType())) {
            emptyViewRender.performBindView(EmptyListModel(), holder as DefaultEmptyStateViewHolder)
        } else {
            val item = getItemFromList<ViewModelData>(position)
            val renderer = getRenderer(item) as ViewRenderer<ViewModelData, ViewHolderA>
            if (renderer != null) {
                renderer.performBindView(item, holder)
            } else {
                throw RuntimeException("Not supported View Holder: $holder")
            }
        }
    }

    /**
     * The ItemViewType is the term of the RecyclerView, We never use this term.
     */
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            return when (networkState) {
                NetworkRequestState.INIT -> getTypeIndex(shimmerViewRender.getType())
                NetworkRequestState.EMPTY -> getTypeIndex(emptyViewRender.getType())
                else -> getTypeIndex(loadMoreViewRender.getType())
            }
        } else {
            getTypeIndex(position)
        }
    }

    override fun getItemCount(): Int {
        return itemSortedList.size() + if (hasExtraRow()) 1 else 0
    }

    /*---------------*/

    fun registerRenderer(renderer: ViewRenderer<*, *>) {
        val type = renderer.getType()
        if (!mTypes.contains(type)) {
            mTypes.add(type)
            mRenderers.add(renderer)
        } else {
            throw RuntimeException("ViewRenderer already registered for this type: $type")
        }
    }

    fun addItem(item: ViewModelData) {
        with(itemSortedList) {
            beginBatchedUpdates()
            add(item)
            endBatchedUpdates()
        }
    }

    fun addItems(items: List<ViewModelData>) {
        itemSortedList.apply {
            beginBatchedUpdates()
            addAll(items)
            endBatchedUpdates()
        }
    }

    fun removeAll(items: List<ViewModelData>) {
        with(itemSortedList) {
            beginBatchedUpdates()
            replaceAll(items)
            endBatchedUpdates()
        }
    }

    fun addItemsAndRemove(items: List<ViewModelData>) {
        with(itemSortedList) {

            beginBatchedUpdates()
            addAll(items)
            endBatchedUpdates()

            val oldList: MutableList<ViewModelData> = mutableListOf()
            var count = itemSortedList.size()
            while (count > 0) {
                count--
                oldList.add(itemSortedList[count])
            }
            val listToRemove = oldList.subtract(items)
            beginBatchedUpdates()
            for (itemToRemove in listToRemove) {
                remove(itemToRemove)
            }
            endBatchedUpdates()
        }
    }

    fun clearAndAddItems(items: List<ViewModelData>) {
        with(itemSortedList) {
            beginBatchedUpdates()
            clear()
            addAll(items)
            endBatchedUpdates()
        }
    }

    fun removeItem(index: Int) {
        with(itemSortedList) {
            if (size() == 0) {
                return
            }
            beginBatchedUpdates()
            remove(itemSortedList.get(index))
            endBatchedUpdates()
        }
    }

    fun removeItem(item: ViewModelData) {
        with(itemSortedList) {
            val index = itemSortedList.indexOf(item)
            if (index == -1) {
                return
            }
            beginBatchedUpdates()
            remove(itemSortedList.get(index))
            endBatchedUpdates()
        }
    }

    fun getSizeList(): Int {
        return itemSortedList.size()
    }

    fun changeSortType(comparator: Comparator<ViewModelData>) {
        with(itemSortedList) {
            sortedListComparatorWrapper.setComparator(comparator)
            beginBatchedUpdates()
            val tempUsers = (0 until itemSortedList.size()).mapTo(ArrayList<ViewModelData>()) { get(it) }
            clear()
            addAll(tempUsers)
            tempUsers.clear()
            endBatchedUpdates()
        }
    }

    fun setNetworkState(newNetworkState: NetworkRequestState?) {
        if (itemSortedList.size() != 0) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(itemCount)
                } else {
                    notifyItemInserted(itemCount)
                }
            } else if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        } else {
            this.networkState = newNetworkState
            notifyDataSetChanged()
        }

    }

    fun <T : ViewModelData> getItemFromList(position: Int): T {
        return itemSortedList.get(position) as T
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkRequestState.LOADED
    }

    private fun getTypeIndex(position: Int): Int {
        val model = getItemFromList<ViewModelData>(position)
        return getTypeIndex(model)
    }

    private fun getTypeIndex(model: ViewModelData): Int {
        return getTypeIndex(model.javaClass)
    }

    private fun getTypeIndex(type: Type): Int {
        val typeIndex = mTypes.indexOf(type)

        if (typeIndex == -1) {
            throw RuntimeException("ViewRenderer not registered for this type: $type")
        }
        return typeIndex
    }

    private fun getRenderer(position: Int): ViewRenderer<*, *> {
        val typeIndex = getTypeIndex(position)
        return mRenderers[typeIndex]
    }

    private fun getRenderer(model: ViewModelData): ViewRenderer<*, *> {
        val typeIndex = getTypeIndex(model)
        return mRenderers[typeIndex]
    }

    private fun getRenderer(type: Type): ViewRenderer<*, *> {
        val typeIndex = getTypeIndex(type)
        return mRenderers[typeIndex]
    }
}