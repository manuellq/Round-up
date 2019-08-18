package com.mlcorrea.roundup.ui.renders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.mlcorrea.roundup.domain.model.ViewModelData
import java.lang.reflect.Type

/**
 * Created by manuel.correa on 19/01/2018.
 */

abstract class ViewRenderer<in M : ViewModelData, VH : ViewHolderA>(private val type: Type) {

    private var context: Context? = null
    /**
     * This method will be called for a full bind.
     *
     * @param model  your a ViewModel
     * @param holder your a ViewHolder
     */
    abstract fun bindView(model: M, holder: VH)

    abstract fun createViewHolder(parent: ViewGroup): VH

    fun performCreateViewHolder(parent: ViewGroup): VH {
        context = parent.context
        return createViewHolder(parent)
    }

    fun performBindView(model: M, holder: VH) {
        holder.setType(model::class.java)
        bindView(model, holder)
    }

    fun getContext(): Context? {
        return context
    }

    /**
     * Called when a view created by your adapter has been recycled.
     */
    fun viewRecycled(holder: ViewHolderA) {}

    fun getType(): Type {
        return type
    }

    protected fun inflate(@LayoutRes layoutID: Int, parent: ViewGroup?, attachToRoot: Boolean): View {
        return LayoutInflater.from(parent?.context).inflate(layoutID, parent, attachToRoot)
    }

    protected fun inflate(@LayoutRes layoutID: Int, parent: ViewGroup?): View {
        return inflate(layoutID, parent, false)
    }
}
