package com.mlcorrea.roundup.ui.renders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mlcorrea.roundup.domain.model.ViewModelData
import com.mlcorrea.roundup.ui.renders.binder.ViewFinder
import com.mlcorrea.roundup.ui.renders.binder.ViewFinderFactory

/**
 * Created by manuel.correa on 22/01/2018.
 */
open class ViewHolderA(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mType: Class<out ViewModelData>? = null
    private var viewFinder: ViewFinder? = null

    fun getViewFinder(): ViewFinder {
        val newViewFinder = viewFinder
        return if (newViewFinder == null) {
            ViewFinderFactory.createViewFinder(itemView)
        } else {
            return newViewFinder
        }
    }

    fun setType(type: Class<out ViewModelData>) {
        mType = type
    }

    /**
     * @return - type of bound ViewModel
     */
    fun getType(): Class<out ViewModelData> {
        return mType ?: throw IllegalArgumentException("View holder must have a type class")
    }
}